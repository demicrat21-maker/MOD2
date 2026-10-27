package com.sundereddepths.mod.quests;

import net.minecraftforge.event.AddReloadListenerEvent;

public final class ModQuests {

    private static final QuestReloadListener LISTENER = new QuestReloadListener();

    public static void register(AddReloadListenerEvent event) {
        event.addListener(LISTENER);
    }

    public static QuestReloadListener listener() {
        return LISTENER;
    }

    private ModQuests() {
    }
}
