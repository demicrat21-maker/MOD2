package com.sundereddepths.mod.events;

import com.sundereddepths.mod.data.PlayerProgress;
import net.minecraft.nbt.Tag;
import net.minecraftforge.event.entity.player.PlayerEvent;

/** Keeps player progress when a player respawns or returns from the End. */
public final class ProgressEvents {

    public static void onPlayerClone(PlayerEvent.Clone event) {
        Tag saved = event.getOriginal().getPersistentData().get(PlayerProgress.KEY);
        if (saved != null) {
            event.getEntity().getPersistentData().put(PlayerProgress.KEY, saved.copy());
        }
    }

    private ProgressEvents() {
    }
}
