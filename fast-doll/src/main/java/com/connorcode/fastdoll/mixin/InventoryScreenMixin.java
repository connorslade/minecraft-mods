package com.connorcode.fastdoll.mixin;

import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import static com.connorcode.fastdoll.FastDoll.client;
import static com.connorcode.fastdoll.FastDoll.enabled;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin implements RecipeBookProvider {
    @ModifyConstant(method = "drawEntity(Lnet/minecraft/entity/LivingEntity;)Lnet/minecraft/client/render/entity/state/EntityRenderState;", constant = @Constant(floatValue = 1f))
    private static float tickDelta(float constant) {
        return (enabled && client.currentScreen instanceof InventoryScreen) ? client.getRenderTickCounter()
                .getTickProgress(false) : constant;
    }
}
