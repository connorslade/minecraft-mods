package com.connorcode.quickdrop.mixin;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.connorcode.quickdrop.QuickDrop.cancelDeathVelocity;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
    @Shadow public ServerPlayerEntity player;

    @Inject(method = "onPlayerMove", at = @At("HEAD"), cancellable = true)
    void onPlayerMove(PlayerMoveC2SPacket packet, CallbackInfo ci) {
        if (cancelDeathVelocity && player.isDead()) ci.cancel();
    }
}
