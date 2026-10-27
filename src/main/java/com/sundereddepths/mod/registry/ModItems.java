package com.sundereddepths.mod.registry;

import com.sundereddepths.mod.core.SundeteredDepthsMod;
import com.sundereddepths.mod.items.ChronoscopeItem;
import com.sundereddepths.mod.items.RegaliaItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, SundeteredDepthsMod.MOD_ID);

    public static final RegistryObject<Item> ROOT_ASH_STONE = ITEMS.register("root_ash_stone",
            () -> new BlockItem(ModBlocks.ROOT_ASH_STONE.get(), new Item.Properties()));

    public static final RegistryObject<Item> CHRONOSCOPE = ITEMS.register("chronoscope",
            () -> new ChronoscopeItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> CROWN_OF_ROOTS = ITEMS.register("crown_of_roots",
            () -> new RegaliaItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant(),
                    RegaliaItem.RegaliaEffect.ROOT_VITALITY));

    public static final RegistryObject<Item> ROOT_WRAITH_SPAWN_EGG = ITEMS.register("root_wraith_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.ROOT_WRAITH, 0x3b2f2f, 0x8faf6a, new Item.Properties()));

    private ModItems() {
    }
}
