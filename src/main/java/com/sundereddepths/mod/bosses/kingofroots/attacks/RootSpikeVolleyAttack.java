package com.sundereddepths.mod.bosses.kingofroots.attacks;

import com.sundereddepths.mod.bosses.AbstractBossEntity;
import com.sundereddepths.mod.bosses.BossAttack;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

/**
 * Phase 2 ranged attack: a line of ground spikes erupting toward the
 * target, forcing lateral movement instead of just tanking hits. This is
 * the "new attacks" requirement for phase 2 from GAME_DESIGN.md's phase
 * template (BOSSES.md section).
 */
public class RootSpikeVolleyAttack implements BossAttack {

    private static final double RANGE = 12.0;
    private static final float DAMAGE = 8.0f;

    @Override
    public boolean canUse(AbstractBossEntity boss, LivingEntity target) {
        return boss.distanceTo(target) <= RANGE && boss.distanceTo(target) > 3.0;
    }

    @Override
    public void telegraph(AbstractBossEntity boss) {
        if (boss.level() instanceof ServerLevel serverLevel) {
            var target = boss.getTarget();
            if (target == null) return;
            Vec3 direction = target.position().subtract(boss.position()).normalize();
            for (int i = 1; i <= 6; i++) {
                Vec3 point = boss.position().add(direction.scale(i * 2.0));
                serverLevel.sendParticles(ParticleTypes.CRIMSON_SPORE,
                        point.x, point.y + 0.2, point.z, 6, 0.3, 0.1, 0.3, 0.01);
            }
        }
    }

    @Override
    public void execute(AbstractBossEntity boss, LivingEntity target) {
        // Damage lands if the target hasn't moved out of the telegraphed line.
        Vec3 direction = target.position().subtract(boss.position()).normalize();
        Vec3 predictedLine = boss.position().add(direction.scale(boss.distanceTo(target)));
        if (predictedLine.distanceTo(target.position()) < 1.5) {
            target.hurt(boss.damageSources().mobAttack(boss), DAMAGE);
        }
    }

    @Override
    public int telegraphTicks() {
        return 20;
    }

    @Override
    public int cooldownTicks() {
        return 50;
    }
}
