package com.connorcode.fastdoll.mixin;

import com.connorcode.fastdoll.FastDoll;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin implements RecipeBookProvider {
    @Inject(method = "method_64045(Lnet/minecraft/client/render/entity/EntityRenderDispatcher;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/render/VertexConsumerProvider;)V", at = @At("HEAD"), cancellable = true)
    private static void onDrawEntity(EntityRenderDispatcher entityRenderDispatcher, LivingEntity entity, DrawContext context, VertexConsumerProvider vertexConsumers, CallbackInfo ci) {
        if (!FastDoll.enabled) return;

        var tickDelta = FastDoll.client.getRenderTickCounter().getTickProgress(false);

        float h = entity.lastBodyYaw;
        float i = entity.lastYaw;
        float j = entity.lastPitch;
        float k = entity.lastHeadYaw;
        entity.lastBodyYaw = entity.bodyYaw;
        entity.lastYaw = entity.getYaw();
        entity.lastPitch = entity.getPitch();
        entity.lastHeadYaw = entity.headYaw;
        entityRenderDispatcher.render(entity, 0.0, 0.0, 0.0, tickDelta, context.getMatrices(), vertexConsumers, 0xF000F0);
        entity.lastBodyYaw = h;
        entity.lastYaw = i;
        entity.lastPitch = j;
        entity.lastHeadYaw = k;
        ci.cancel();
    }
}
