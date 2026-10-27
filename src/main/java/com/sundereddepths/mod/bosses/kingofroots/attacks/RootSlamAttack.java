package com.sundereddepths.mod.bosses.kingofroots.attacks;

import com.sundereddepths.mod.bosses.AbstractBossEntity;
import com.sundereddepths.mod.bosses.BossAttack;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

/**
 * Phase 1 basic attack: a slow overhead slam that deals heavy melee damage
 * in a small radius. Long telegraph (fair reaction window) matching the
 * GDD's "боссы должны телеграфировать атаки" requirement.
 */
public class RootSlamAttack implements BossAttack {

    private static final double RANGE = 4.0;
    private static final float DAMAGE = 14.0f;

    @Override
    public boolean canUse(AbstractBossEntity boss, LivingEntity target) {
        return boss.distanceTo(target) <= RANGE;
    }

    @Override
    public void telegraph(AbstractBossEntity boss) {
        if (boss.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.CRIT,
                    boss.getX(), boss.getY() + 1.0, boss.getZ(),
                    20, 1.0, 0.5, 1.0, 0.02);
        }
        boss.level().playSound(null, boss.blockPosition(),
                net.minecraft.sounds.SoundEvents.WARDEN_TENDRIL_CLICKS,
                net.minecraft.sounds.SoundSource.HOSTILE, 1.5f, 0.6f);
    }

    @Override
    public void execute(AbstractBossEntity boss, LivingEntity target) {
        if (boss.distanceTo(target) <= RANGE + 1.0) {
            target.hurt(boss.damageSources().mobAttack(boss), DAMAGE);
            target.knockback(1.2, boss.getX() - target.getX(), boss.getZ() - target.getZ());
        }
    }

    @Override
    public int telegraphTicks() {
        return 24; // 1.2s wind-up
    }

    @Override
    public int cooldownTicks() {
        return 40;
    }
}
