package com.sundereddepths.mod.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

/**
 * Abyssal Pressure — GAME_DESIGN.md 2.2. Its amplifier is meant to be set
 * by a per-layer system (a LevelEvent.Tick handler comparing player Y-level
 * to each layer's band, see docs/BIOMES.md for the Y-ranges) that lands in
 * PHASE 10 with world generation. Countered by Regalias and "Ядра Стойкости"
 * gear (crafted per-layer). For now this defines the effect itself and its
 * baseline tick behaviour (minor damage over time, scaling with amplifier)
 * so it's ready to be applied once the layer-detection system exists.
 */
public class AbyssalPressureEffect extends MobEffect {

    public AbyssalPressureEffect() {
        super(MobEffectCategory.HARMFUL, 0x2B0033);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        entity.hurt(entity.damageSources().magic(), 0.5f + (0.5f * amplifier));
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration % 40 == 0; // every 2 seconds, scales in severity via amplifier not frequency
    }
}
