package com.sundereddepths.mod.core;

import com.sundereddepths.mod.effects.ModEffects;
import com.sundereddepths.mod.events.ProgressEvents;
import com.sundereddepths.mod.quests.ModQuests;
import com.sundereddepths.mod.registry.ModBlocks;
import com.sundereddepths.mod.registry.ModCreativeTabs;
import com.sundereddepths.mod.registry.ModEntityAttributes;
import com.sundereddepths.mod.registry.ModEntityTypes;
import com.sundereddepths.mod.registry.ModItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Entry point (Forge 1.21.1). Keep this class thin: it only wires registries and listeners. */
@Mod(SundeteredDepthsMod.MOD_ID)
public class SundeteredDepthsMod {

    public static final String MOD_ID = "sundereddepths";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @SuppressWarnings("removal")
    public SundeteredDepthsMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModEntityTypes.ENTITY_TYPES.register(modEventBus);
        ModEffects.MOB_EFFECTS.register(modEventBus);
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);

        modEventBus.addListener(ModEntityAttributes::register);

        MinecraftForge.EVENT_BUS.addListener(ModQuests::register);
        MinecraftForge.EVENT_BUS.addListener(ProgressEvents::onPlayerClone);

        LOGGER.info("Sundered Depths initializing (Forge).");
    }
}
