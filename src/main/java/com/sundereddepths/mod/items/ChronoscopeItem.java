package com.sundereddepths.mod.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

/**
 * The Chronoscope: the player's tool for switching a layer's active Echo.
 * Interaction shell only: a 3-second channel, then onEchoChannelComplete
 * (the full Echo system lands in a later phase).
 */
public class ChronoscopeItem extends Item {

    /** 3 seconds. */
    public static final int CHANNEL_TICKS = 60;

    public ChronoscopeItem(Properties properties) {
        super(properties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return CHANNEL_TICKS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeCharged) {
        int channeled = getUseDuration(stack, entity) - timeCharged;
        if (channeled >= CHANNEL_TICKS && !level.isClientSide && entity instanceof Player player) {
            onEchoChannelComplete(level, player);
        }
    }

    protected void onEchoChannelComplete(Level level, Player player) {
        player.displayClientMessage(
                Component.translatable("item.sundereddepths.chronoscope.channel_complete"),
                true
        );
    }
}
