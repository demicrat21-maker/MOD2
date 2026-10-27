package com.sundereddepths.mod.bosses;

import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerBossEvent;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Base class every Sundered Depths boss extends (King-in-Ash, mini-bosses,
 * hidden bosses, endgame remakes). See ARCHITECTURE.md 3.2 for the design
 * rationale: subclasses only need to implement {@link #definePhases()} —
 * everything else (boss bar, phase transitions, attack ticking) is handled
 * here once.
 * <p>
 * {@link #arena} is intentionally nullable: mini-bosses and hidden bosses
 * don't always get a dedicated BossArena (that system lands in PHASE 6/7
 * alongside the first fully-realized King-in-Ash).
 */
public abstract class AbstractBossEntity extends Monster {

    private final ServerBossEvent bossEvent;
    private PhaseController phaseController;

    @Nullable
    protected Object arena; // typed as BossArena once that system exists (PHASE 6)

    private BossAttack pendingAttack;
    private int telegraphTicksRemaining;
    private int globalAttackCooldown;

    protected AbstractBossEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.bossEvent = new ServerBossEvent(this.getDisplayName(),
                BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.NOTCHED_10);
    }

    /**
     * Subclasses define their phase list here (GDD section 6: min 3 phases,
     * ordered by hpThreshold — see {@link BossPhase} and {@link PhaseController}).
     */
    protected abstract List<BossPhase> definePhases();

    private PhaseController phases() {
        if (phaseController == null) {
            phaseController = new PhaseController(definePhases());
        }
        return phaseController;
    }

    @Override
    public void tick() {
        super.tick();

        if (!level().isClientSide) {
            phases().tick(this);
            tickAttackSelection();
        }

        bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }

    private void tickAttackSelection() {
        if (globalAttackCooldown > 0) {
            globalAttackCooldown--;
            return;
        }

        if (pendingAttack != null) {
            telegraphTicksRemaining--;
            if (telegraphTicksRemaining <= 0) {
                var target = getTarget();
                if (target != null) {
                    pendingAttack.execute(this, target);
                }
                globalAttackCooldown = pendingAttack.cooldownTicks();
                pendingAttack = null;
            }
            return;
        }

        var target = getTarget();
        if (target == null || phaseController.getCurrentPhase() == null) {
            return;
        }

        for (BossAttack attack : phaseController.getCurrentPhase().attacks()) {
            if (attack.canUse(this, target)) {
                pendingAttack = attack;
                telegraphTicksRemaining = attack.telegraphTicks();
                attack.telegraph(this);
                break;
            }
        }
    }

    @Override
    public void startSeenByPlayer(net.minecraft.server.level.ServerPlayer player) {
        super.startSeenByPlayer(player);
        bossEvent.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(net.minecraft.server.level.ServerPlayer player) {
        super.stopSeenByPlayer(player);
        bossEvent.removePlayer(player);
    }

    public ServerBossEvent getBossEvent() {
        return bossEvent;
    }
}
