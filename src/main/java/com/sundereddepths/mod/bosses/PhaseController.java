package com.sundereddepths.mod.bosses;

import com.sundereddepths.mod.core.SundeteredDepthsMod;

import java.util.List;
import java.util.Optional;

/**
 * Drives phase transitions for a single boss instance. Kept out of
 * {@link AbstractBossEntity} so the entity class stays focused on being an
 * entity, not on phase bookkeeping (ARCHITECTURE.md 3.2 — no God-classes).
 * <p>
 * Phases are expected to be supplied ordered from highest hpThreshold to
 * lowest (phase 1 first). The controller finds the *lowest-threshold* phase
 * whose threshold is still >= current HP fraction, which lets phases be
 * defined independently of each other and re-ordered safely.
 */
public class PhaseController {

    private final List<BossPhase> phases;
    private BossPhase currentPhase;

    public PhaseController(List<BossPhase> phases) {
        if (phases.isEmpty()) {
            throw new IllegalArgumentException("A boss must define at least one phase.");
        }
        this.phases = phases;
    }

    /** Call every entity tick. Handles phase transitions and delegates ticking to the active phase. */
    public void tick(AbstractBossEntity boss) {
        double hpFraction = boss.getHealth() / boss.getMaxHealth();
        BossPhase target = resolvePhaseFor(hpFraction);

        if (target != currentPhase) {
            SundeteredDepthsMod.LOGGER.debug("Boss {} transitioning to phase {}",
                    boss.getType().toShortString(), target.id());
            currentPhase = target;
            currentPhase.onEnter(boss);
        }

        currentPhase.tick(boss);
    }

    private BossPhase resolvePhaseFor(double hpFraction) {
        // A phase "qualifies" once HP has dropped to or below its threshold.
        // Sorting ascending and taking the first qualifying phase gives us
        // the most-progressed (deepest) phase the current HP satisfies.
        Optional<BossPhase> match = phases.stream()
                .sorted((a, b) -> Double.compare(a.hpThreshold(), b.hpThreshold()))
                .filter(p -> hpFraction <= p.hpThreshold())
                .findFirst();
        return match.orElse(phases.get(phases.size() - 1));
    }

    public BossPhase getCurrentPhase() {
        return currentPhase;
    }
}
