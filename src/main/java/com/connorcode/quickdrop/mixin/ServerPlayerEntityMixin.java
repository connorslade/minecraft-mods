package com.connorcode.quickdrop.mixin;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static com.connorcode.quickdrop.QuickDrop.multiplier;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
    @Redirect(method = "dropItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;"))
    ItemEntity onDropItem(PlayerEntity instance, ItemStack stack, boolean throwRandomly, boolean retainOwnership) {
        if (stack.isEmpty()) return null;
        PlayerEntity player = (PlayerEntity) (Object) this;
        Random random = player.getRandom();
        World world = player.world;

        double m = multiplier;
        if (world.isClient) player.swingHand(Hand.MAIN_HAND);
        double d = player.getEyeY() - 0.30000001192092896;

        ItemEntity itemEntity = new ItemEntity(world, player.getX(), d, player.getZ(), stack);
        itemEntity.setPickupDelay(40);

        if (throwRandomly) {
            float f = random.nextFloat() * 0.5F;
            float g = random.nextFloat() * 6.2831855F;
            itemEntity.setVelocity((-MathHelper.sin(g) * f) * m, 0.20000000298023224 * m, MathHelper.cos(g) * f * m);
            return itemEntity;
        }

        float g = MathHelper.sin(player.getPitch() * 0.017453292F);
        float h = MathHelper.cos(player.getPitch() * 0.017453292F);
        float i = MathHelper.sin(player.getYaw() * 0.017453292F);
        float j = MathHelper.cos(player.getYaw() * 0.017453292F);
        float k = random.nextFloat() * 6.2831855F;
        float l = 0.02F * random.nextFloat();
        itemEntity.setVelocity((double) (-i * h * 0.3F) + Math.cos(k) * (double) l,
                -g * 0.3F + 0.1F + (random.nextFloat() - random.nextFloat()) * 0.1F,
                (double) (j * h * 0.3F) + Math.sin(k) * (double) l);
        return itemEntity;
    }
}

