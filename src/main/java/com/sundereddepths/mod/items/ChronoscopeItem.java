package com.sundereddepths.mod.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

/**
 * The Chronoscope: the player's tool for switching a layer's active Echo
 * (see GAME_DESIGN.md 2.3). Full implementation lands in PHASE 10 alongside
 * the EchoManager (see ARCHITECTURE.md 3.3) — this is the interaction shell:
 * a 3-second channel (matching the design's "not instant, no combat-switch
 * cheese" requirement), then hands off to EchoManager once that exists.
 * <p>
 * Deliberately left as a clear extension point: {@link #onEchoChannelComplete}
 * is the single method later PHASEs need to wire up.
 */
public class ChronoscopeItem extends Item {

    /** 3 seconds, matching GAME_DESIGN.md 2.3. */
    public static final int CHANNEL_TICKS = 60;

    public ChronoscopeItem(Properties properties) {
        super(properties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW; // channel-style hold, repurposed as a placeholder animation
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return CHANNEL_TICKS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, net.minecraft.world.InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, net.minecraft.world.entity.LivingEntity entity, int timeCharged) {
        int channeled = getUseDuration(stack) - timeCharged;
        if (channeled >= CHANNEL_TICKS && !level.isClientSide && entity instanceof Player player) {
            onEchoChannelComplete(level, player);
        }
    }

    /**
     * Extension point for PHASE 10 (EchoManager). Currently just informs the
     * player so the item is testable end-to-end before EchoManager exists.
     */
    protected void onEchoChannelComplete(Level level, Player player) {
        player.displayClientMessage(
                Component.translatable("item.sundereddepths.chronoscope.channel_complete"),
                true
        );
        // TODO(PHASE 10): EchoManager.get(level).cycleEcho(player.blockPosition(), RADIUS);
    }
}
