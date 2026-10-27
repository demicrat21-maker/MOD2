package com.sundereddepths.mod.bosses.kingofroots;

import com.sundereddepths.mod.bosses.AbstractBossEntity;
import com.sundereddepths.mod.bosses.BossAttack;
import com.sundereddepths.mod.bosses.BossPhase;
import com.sundereddepths.mod.bosses.kingofroots.attacks.RootSlamAttack;
import com.sundereddepths.mod.bosses.kingofroots.attacks.RootSpikeVolleyAttack;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

import java.util.List;

/**
 * Phase 2 (below 60% HP): the King wakes fully, gains a ranged attack.
 * Matches ТЗ section 6's requirement: "После потери 60% HP босс меняет
 * поведение. Появляются: новые атаки".
 */
public class KingOfRootsPhase2 implements BossPhase {

    private final List<BossAttack> attacks = List.of(new RootSlamAttack(), new RootSpikeVolleyAttack());

    @Override
    public double hpThreshold() {
        return 0.6;
    }

    @Override
    public void onEnter(AbstractBossEntity boss) {
        if (boss.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.SOUL_FIRE_FLAME,
                    boss.getX(), boss.getY() + 1.5, boss.getZ(), 40, 1.0, 1.0, 1.0, 0.05);
        }
        boss.level().playSound(null, boss.blockPosition(),
                SoundEvents.WARDEN_ROAR, SoundSource.HOSTILE, 2.0f, 0.7f);
    }

    @Override
    public void tick(AbstractBossEntity boss) {
        // Placeholder for arena root-growth hazard, added when BossArena (PHASE 6) exists.
    }

    @Override
    public List<BossAttack> attacks() {
        return attacks;
    }

    @Override
    public String id() {
        return "phase_2_full_awakening";
    }
}
