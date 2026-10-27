package com.sundereddepths.mod.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.TooltipContext;

import java.util.List;

/**
 * Base class for every Regalia (crown dropped by a King-in-Ash).
 * See GAME_DESIGN.md 2.4: max 3 worn at once, each grants a passive and
 * unlocks that layer's Void Echo. The 3-slot equip/unequip container itself
 * (ARCHITECTURE.md 3.4) is a PHASE 6 deliverable, built alongside the first
 * full boss (King of Roots) so the reward loop can be tested end-to-end.
 * <p>
 * This class only defines *what a Regalia is and what it grants* — kept
 * separate from *how it's worn* so the slot-container implementation can
 * change later (own container vs. Curios-style API) without touching every
 * Regalia item.
 */
public class RegaliaItem extends Item {

    public enum RegaliaEffect {
        /** Crown of Roots: passive regen + Abyssal Pressure resist on Layers 1-2. */
        ROOT_VITALITY
        // Later Regalias (Ash-Court, Crystal, Sky, Frost) added as PHASE 7-9 land.
    }

    private final RegaliaEffect effect;

    public RegaliaItem(Properties properties, RegaliaEffect effect) {
        super(properties);
        this.effect = effect;
    }

    public RegaliaEffect getEffect() {
        return effect;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.sundereddepths.regalia." + effect.name().toLowerCase() + ".desc"));
        tooltip.add(Component.translatable("item.sundereddepths.regalia.slot_reminder")); // "Equip in the Regalia slots (max 3)"
    }
}
