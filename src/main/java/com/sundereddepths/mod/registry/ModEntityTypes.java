package com.sundereddepths.mod.registry;

import com.sundereddepths.mod.bosses.kingofroots.KingOfRootsEntity;
import com.sundereddepths.mod.core.SundeteredDepthsMod;
import com.sundereddepths.mod.entities.RootWraithEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SundeteredDepthsMod.MOD_ID);

    public static final RegistryObject<EntityType<KingOfRootsEntity>> KING_OF_ROOTS =
            ENTITY_TYPES.register("king_of_roots", () -> EntityType.Builder.of(
                            KingOfRootsEntity::new, MobCategory.MONSTER)
                    .sized(1.4f, 2.6f)
                    .fireImmune()
                    .clientTrackingRange(48)
                    .build("king_of_roots"));

    public static final RegistryObject<EntityType<RootWraithEntity>> ROOT_WRAITH =
            ENTITY_TYPES.register("root_wraith", () -> EntityType.Builder.of(
                            RootWraithEntity::new, MobCategory.MONSTER)
                    .sized(0.6f, 1.8f)
                    .clientTrackingRange(16)
                    .build("root_wraith"));

    private ModEntityTypes() {
    }
}
