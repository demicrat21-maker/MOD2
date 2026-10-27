package com.sundereddepths.mod.registry;

import com.sundereddepths.mod.core.SundeteredDepthsMod;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, SundeteredDepthsMod.MOD_ID);

    public static final RegistryObject<Block> ROOT_ASH_STONE = BLOCKS.register(
            "root_ash_stone",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(2.0f, 6.0f)
                    .sound(SoundType.DEEPSLATE)
                    .requiresCorrectToolForDrops())
    );

    private ModBlocks() {
    }
}
