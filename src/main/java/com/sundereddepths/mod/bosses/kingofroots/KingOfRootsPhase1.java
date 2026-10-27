package com.sundereddepths.mod.bosses.kingofroots;

import com.sundereddepths.mod.bosses.AbstractBossEntity;
import com.sundereddepths.mod.bosses.BossAttack;
import com.sundereddepths.mod.bosses.BossPhase;
import com.sundereddepths.mod.bosses.kingofroots.attacks.RootSlamAttack;

import java.util.List;

/** Phase 1 (100%-60% HP): basic melee patterns only. See GAME_DESIGN.md phase template. */
public class KingOfRootsPhase1 implements BossPhase {

    private final List<BossAttack> attacks = List.of(new RootSlamAttack());

    @Override
    public double hpThreshold() {
        return 1.0;
    }

    @Override
    public void onEnter(AbstractBossEntity boss) {
        boss.getBossEvent().setName(boss.getDisplayName());
    }

    @Override
    public void tick(AbstractBossEntity boss) {
        // No per-tick phase effects yet; arena/room changes land with PHASE 6/7's BossArena.
    }

    @Override
    public List<BossAttack> attacks() {
        return attacks;
    }

    @Override
    public String id() {
        return "phase_1_awakening";
    }
}
