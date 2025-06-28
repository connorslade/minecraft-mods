package com.connorcode.fastdoll.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static com.connorcode.fastdoll.FastDoll.client;
import static com.connorcode.fastdoll.FastDoll.enabled;
import static net.minecraft.client.gui.screen.ingame.InventoryScreen.drawEntity;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin implements RecipeBookProvider {
    @Redirect(
            method = "drawEntity(Lnet/minecraft/client/gui/DrawContext;IIIIIFFFLnet/minecraft/entity/LivingEntity;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/InventoryScreen;drawEntity(Lnet/minecraft/client/gui/DrawContext;IIIIFLorg/joml/Vector3f;Lorg/joml/Quaternionf;Lorg/joml/Quaternionf;Lnet/minecraft/entity/LivingEntity;)V")
    )
    private static void onDrawEntity(DrawContext context, int x1, int y1, int x2, int y2, float scale, Vector3f translation, Quaternionf rotation, Quaternionf overrideCameraAngle, LivingEntity entity) {
        if (enabled && client.currentScreen instanceof InventoryScreen) {
            float h = entity.lastBodyYaw;
            float i = entity.lastYaw;
            float j = entity.lastPitch;
            float k = entity.lastHeadYaw;
            entity.lastBodyYaw = entity.bodyYaw;
            entity.lastYaw = entity.getYaw();
            entity.lastPitch = entity.getPitch();
            entity.lastHeadYaw = entity.headYaw;
            drawEntity(context, x1, y1, x2, y2, scale, translation, rotation, overrideCameraAngle, entity);
            entity.lastBodyYaw = h;
            entity.lastYaw = i;
            entity.lastPitch = j;
            entity.lastHeadYaw = k;
        } else {
            drawEntity(context, x1, y1, x2, y2, scale, translation, rotation, overrideCameraAngle, entity);
        }
    }

    @Redirect(
            method = "drawEntity(Lnet/minecraft/client/gui/DrawContext;IIIIFLorg/joml/Vector3f;Lorg/joml/Quaternionf;Lorg/joml/Quaternionf;Lnet/minecraft/entity/LivingEntity;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/EntityRenderer;getAndUpdateRenderState(Lnet/minecraft/entity/Entity;F)Lnet/minecraft/client/render/entity/state/EntityRenderState;")
    )
    private static EntityRenderState onGetUpdateRenderState(EntityRenderer<Entity, EntityRenderState> instance, Entity entity, float tickProgress) {
        var tickDelta = (enabled && client.currentScreen instanceof InventoryScreen) ? client.getRenderTickCounter().getTickProgress(false) : tickProgress;
        return instance.getAndUpdateRenderState(entity, tickDelta);
    }
}
