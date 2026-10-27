package com.sundereddepths.mod.registry;

import com.sundereddepths.mod.bosses.kingofroots.KingOfRootsEntity;
import com.sundereddepths.mod.entities.RootWraithEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;

public final class ModEntityAttributes {

    public static void register(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.KING_OF_ROOTS.get(), KingOfRootsEntity.createAttributes().build());
        event.put(ModEntityTypes.ROOT_WRAITH.get(), RootWraithEntity.createAttributes().build());
    }

    private ModEntityAttributes() {
    }
}
