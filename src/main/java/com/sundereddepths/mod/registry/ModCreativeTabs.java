package com.sundereddepths.mod.registry;

import com.sundereddepths.mod.core.SundeteredDepthsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SundeteredDepthsMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> SUNDERED_DEPTHS_TAB =
            CREATIVE_MODE_TABS.register("sundereddepths_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.sundereddepths"))
                    .icon(() -> new ItemStack(ModItems.CROWN_OF_ROOTS.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.ROOT_ASH_STONE.get());
                        output.accept(ModItems.CHRONOSCOPE.get());
                        output.accept(ModItems.CROWN_OF_ROOTS.get());
                        output.accept(ModItems.ROOT_WRAITH_SPAWN_EGG.get());
                    })
                    .build());

    private ModCreativeTabs() {
    }
}
