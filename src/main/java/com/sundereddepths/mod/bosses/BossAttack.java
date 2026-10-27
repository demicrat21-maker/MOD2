package com.sundereddepths.mod.bosses;

import net.minecraft.world.entity.LivingEntity;

/**
 * One attack a boss can perform during a given phase. See ARCHITECTURE.md
 * 3.2 — attacks are Strategy-pattern objects so a boss class never grows
 * into a God-class full of inline attack logic.
 */
public interface BossAttack {

    /** Whether this attack is usable right now (range, line of sight, cooldown already checked externally). */
    boolean canUse(AbstractBossEntity boss, LivingEntity target);

    /**
     * Visual/audio warning shown before the attack actually lands, giving
     * the player a fair window to react (GDD requirement: боссы должны
     * телеграфировать атаки).
     */
    void telegraph(AbstractBossEntity boss);

    /** The actual attack execution, called after the telegraph window elapses. */
    void execute(AbstractBossEntity boss, LivingEntity target);

    /** Ticks of telegraph time before {@link #execute} is called. */
    int telegraphTicks();

    /** Cooldown in ticks before this specific attack can be chosen again. */
    int cooldownTicks();
}
