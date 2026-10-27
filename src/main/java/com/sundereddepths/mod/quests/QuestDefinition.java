package com.sundereddepths.mod.quests;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

/**
 * Data-driven quest definition (ARCHITECTURE.md 3.5). Loaded from
 * data/sundereddepths/quest/*.json via {@link QuestReloadListener}, so
 * designers/writers can add quests without touching Java code — matching
 * ТЗ section 17's requirement for a full quest system with chains, hidden
 * quests, and non-"kill 10 zombies" objectives.
 * <p>
 * {@code objectives} is deliberately just a list of free-form string ids
 * for now (e.g. "defeat:king_of_roots", "explore:root_ash_shrine",
 * "dialogue:elder_ghost_intro"). A typed Objective sealed-interface (with
 * per-type completion logic) is the natural next step once the quest UI
 * (PHASE 11/12) needs to render progress per-objective-type — kept simple
 * here so the loader/format is provably working first.
 */
public record QuestDefinition(
        String titleKey,
        String descriptionKey,
        List<String> objectives,
        List<ResourceLocation> prerequisiteQuests,
        boolean hidden
) {
    public static final Codec<QuestDefinition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("title_key").forGetter(QuestDefinition::titleKey),
            Codec.STRING.fieldOf("description_key").forGetter(QuestDefinition::descriptionKey),
            Codec.STRING.listOf().fieldOf("objectives").forGetter(QuestDefinition::objectives),
            ResourceLocation.CODEC.listOf().optionalFieldOf("prerequisite_quests", List.of())
                    .forGetter(QuestDefinition::prerequisiteQuests),
            Codec.BOOL.optionalFieldOf("hidden", false).forGetter(QuestDefinition::hidden)
    ).apply(instance, QuestDefinition::new));
}
