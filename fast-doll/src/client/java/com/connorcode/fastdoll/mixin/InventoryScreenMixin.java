package com.connorcode.fastdoll.mixin;

import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import static com.connorcode.fastdoll.FastDoll.client;
import static com.connorcode.fastdoll.FastDoll.enabled;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin implements RecipeUpdateListener {
    @ModifyConstant(method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/client/renderer/entity/state/EntityRenderState;", constant = @Constant(floatValue = 1f))
    private static float tickDelta(float constant) {
        return (enabled && client.screen instanceof InventoryScreen) ? client.getDeltaTracker()
                .getGameTimeDeltaPartialTick(false) : constant;
    }
}
