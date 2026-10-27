package com.sundereddepths.mod.bosses;

import java.util.List;

/**
 * One phase of a boss fight (GDD section 6: minimum 3 phases per major
 * boss, some 4-5). A phase is data + behaviour: at what HP% it kicks in,
 * what happens once (onEnter — e.g. arena change, add spawns) and what
 * attack pool is active while it's running.
 */
public interface BossPhase {

    /** HP fraction (0.0-1.0) at or below which this phase becomes active. */
    double hpThreshold();

    /** Called once when the boss transitions into this phase. */
    void onEnter(AbstractBossEntity boss);

    /** Called every tick while this phase is active, after onEnter. */
    void tick(AbstractBossEntity boss);

    /** The attacks available while this phase is active. */
    List<BossAttack> attacks();

    /** Human-readable id for logging/debugging, e.g. "phase_2_ash_storm". */
    String id();
}
