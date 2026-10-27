package com.sundereddepths.mod.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.HashSet;
import java.util.Set;

/**
 * Per-player persistent progress: completed quests and owned Regalias
 * (GAME_DESIGN.md 2.4/section 17). Stored in the player's persistent NBT via a Codec
 * (see {@link #load} / {@link #save}), which works on Forge without capabilities.
 * <p>
 * Mutable on purpose — attachments are stored by reference and mutated in
 * place, then the attachment is marked dirty by the caller for saving.
 */
public class PlayerProgress {

    public static final String KEY = "sundereddepths_progress";

    private final Set<ResourceLocation> completedQuests = new HashSet<>();
    private final Set<ResourceLocation> ownedRegalia = new HashSet<>();

    public static final Codec<PlayerProgress> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.listOf().fieldOf("completed_quests")
                    .forGetter(p -> p.completedQuests.stream().toList()),
            ResourceLocation.CODEC.listOf().fieldOf("owned_regalia")
                    .forGetter(p -> p.ownedRegalia.stream().toList())
    ).apply(instance, (completed, owned) -> {
        PlayerProgress progress = new PlayerProgress();
        progress.completedQuests.addAll(completed);
        progress.ownedRegalia.addAll(owned);
        return progress;
    }));

    /** Reads this player's progress from their persistent data (empty progress if none saved). */
    public static PlayerProgress load(Player player) {
        var tag = player.getPersistentData().get(KEY);
        if (tag == null) {
            return new PlayerProgress();
        }
        return CODEC.parse(NbtOps.INSTANCE, tag).result().orElseGet(PlayerProgress::new);
    }

    /** Writes this progress back to the player's persistent data. */
    public void save(Player player) {
        CODEC.encodeStart(NbtOps.INSTANCE, this).result()
                .ifPresent(tag -> player.getPersistentData().put(KEY, tag));
    }

    public boolean hasCompleted(ResourceLocation questId) {
        return completedQuests.contains(questId);
    }

    public void markCompleted(ResourceLocation questId) {
        completedQuests.add(questId);
    }

    public boolean ownsRegalia(ResourceLocation regaliaId) {
        return ownedRegalia.contains(regaliaId);
    }

    public void grantRegalia(ResourceLocation regaliaId) {
        ownedRegalia.add(regaliaId);
    }

    public Set<ResourceLocation> getOwnedRegalia() {
        return Set.copyOf(ownedRegalia);
    }
}
