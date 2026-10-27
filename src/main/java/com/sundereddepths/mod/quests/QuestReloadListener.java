package com.sundereddepths.mod.quests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import com.sundereddepths.mod.core.SundeteredDepthsMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Loads every quest definition from {@code data/<namespace>/quest/*.json}
 * in any active datapack (built-in or user-added), so quest content can be
 * added/edited without recompiling the mod (ARCHITECTURE.md 3.5).
 */
public class QuestReloadListener extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new Gson();
    private Map<ResourceLocation, QuestDefinition> quests = Map.of();

    public QuestReloadListener() {
        super(GSON, "quest");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> resourceMap, ResourceManager resourceManager, ProfilerFiller profiler) {
        Map<ResourceLocation, QuestDefinition> built = new HashMap<>();
        for (Entry<ResourceLocation, JsonElement> entry : resourceMap.entrySet()) {
            QuestDefinition.CODEC.parse(JsonOps.INSTANCE, entry.getValue())
                    .resultOrPartial(error -> SundeteredDepthsMod.LOGGER.error(
                            "Failed to parse quest {}: {}", entry.getKey(), error))
                    .ifPresent(quest -> built.put(entry.getKey(), quest));
        }
        this.quests = Map.copyOf(built);
        SundeteredDepthsMod.LOGGER.info("Loaded {} quest definitions.", quests.size());
    }

    public Map<ResourceLocation, QuestDefinition> getQuests() {
        return quests;
    }
}
