package com.sundereddepths.mod.effects;

import com.sundereddepths.mod.core.SundeteredDepthsMod;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, SundeteredDepthsMod.MOD_ID);

    public static final RegistryObject<MobEffect> ABYSSAL_PRESSURE =
            MOB_EFFECTS.register("abyssal_pressure", AbyssalPressureEffect::new);

    private ModEffects() {
    }
}
