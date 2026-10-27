package com.sundereddepths.mod.entities;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * Root Wraith — the common enemy of Layer 1 (Корни). GAME_DESIGN.md /
 * ТЗ section 24 asks for varied enemy behaviours, not just melee grunts;
 * this one is the "засадный" (ambush) archetype: it turns near-invisible
 * while it has no target and reveals itself the moment it commits to an
 * attack, rewarding players who move carefully through root-choked areas
 * instead of just fighting on sight.
 * <p>
 * Deliberately simple (vanilla goal classes only) so it's a solid template
 * to copy for the other Layer 1-2 mobs still to come, rather than a one-off
 * special case.
 */
public class RootWraithEntity extends Monster {

    public RootWraithEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 16.0)
                .add(Attributes.MOVEMENT_SPEED, 0.28)
                .add(Attributes.ATTACK_DAMAGE, 4.0)
                .add(Attributes.FOLLOW_RANGE, 20.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.15, true));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            updateAmbushVisibility();
        }
    }

    /** No target = fades toward invisible; has a target = fully visible (the "reveal"). */
    private void updateAmbushVisibility() {
        boolean hasTarget = getTarget() != null;
        if (!hasTarget) {
            if (!hasEffect(MobEffects.INVISIBILITY)) {
                addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 40, 0, false, false));
            }
        } else {
            removeEffect(MobEffects.INVISIBILITY);
        }
    }
}
