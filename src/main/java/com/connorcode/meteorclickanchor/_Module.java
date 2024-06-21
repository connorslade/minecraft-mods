package com.connorcode.meteorclickanchor;

import meteordevelopment.meteorclient.events.entity.player.PlaceBlockEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;

public class _Module extends Module {
    private final Setting<Integer> chargeDelay = settings.getDefaultGroup()
            .add(new IntSetting.Builder().name("Charge Delay")
                    .description("The delay in ticks between placing and charging the anchor.").defaultValue(5).min(0)
                    .sliderMax(20).build());
    private final Setting<Integer> chargeDelayVariation = settings.getDefaultGroup()
            .add(new IntSetting.Builder().name("Charge Delay Variation")
                    .description("Number of ticks to randomly add or subtract from the charge delay.").defaultValue(0)
                    .min(0).sliderMax(20).build());
    private final Setting<Integer> detonateDelay = settings.getDefaultGroup()
            .add(new IntSetting.Builder().name("Detonate Delay")
                    .description("The delay in ticks between charging and detonating the anchor.").defaultValue(5)
                    .min(0).sliderMax(20).build());
    private final Setting<Integer> detonateDelayVariation = settings.getDefaultGroup()
            .add(new IntSetting.Builder().name("Detonate Delay Variation")
                    .description("Number of ticks to randomly add or subtract from the detonate delay.").defaultValue(0)
                    .min(0).sliderMax(20).build());
    private final Setting<Integer> maxRange = settings.getDefaultGroup()
            .add(new IntSetting.Builder().name("Max Range")
                    .description("The maximum distance the anchor can be from the player.").defaultValue(5).min(0)
                    .sliderMax(20).build());

    private final Deque<ScheduledTask> tasks = new LinkedList<>();
    private long tick = 0;

    public _Module() {
        super(Categories.Combat, "Click Anchor", "Automatically charges and detonates respawn anchors when places.");
    }

    @Override
    public void onActivate() {
        this.tasks.clear();
        this.tick = 0;
    }

    @EventHandler
    void onTick(TickEvent.Pre tick) {
        if (!isActive()) return;
        this.tick++;
        ScheduledTask task = this.tasks.peek();
        if (task != null && this.tick >= task.tick) {
            task.runnable.run();
            this.tasks.poll();
        }
    }

    @EventHandler
    void onPlace(PlaceBlockEvent event) {
        if (!isActive() || event.block != Blocks.RESPAWN_ANCHOR || Objects.requireNonNull(mc.world).getDimension().respawnAnchorWorks()) return;
        assert mc.interactionManager != null && mc.player != null;

        var delay = tick + chargeDelay.get() + (int) ((2 * Math.random() - 1) * chargeDelayVariation.get());
        this.tasks.add(new ScheduledTask(delay, () -> {
            var glowstone = InvUtils.findInHotbar(Items.GLOWSTONE);
            if (!glowstone.found()) {
                error("No glowstone found in hotbar");
                return;
            }

            var hit = getHitResult(event);
            if (hit == null) return;

            mc.player.setSneaking(false);
            if (glowstone.isOffhand()) {
                mc.interactionManager.interactBlock(mc.player, Hand.OFF_HAND, hit);
            } else {
                InvUtils.swap(glowstone.slot(), true);
                mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, hit);
                InvUtils.swapBack();
            }
        }));

        delay += detonateDelay.get() + (int) ((2 * Math.random() - 1) * detonateDelayVariation.get());
        this.tasks.add(new ScheduledTask(delay, () -> {
            var hit = getHitResult(event);
            if (hit == null) return;

            mc.player.setSneaking(false);
            mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, hit);
        }));
    }

    @Nullable
    private BlockHitResult getHitResult(PlaceBlockEvent event) {
        var hit = new BlockHitResult(event.blockPos.toCenterPos(), Direction.UP, event.blockPos, true);
        var range = maxRange.get();
        if (Objects.requireNonNull(mc.player).getBlockPos().getSquaredDistance(event.blockPos) > range * range) {
            error("Anchor is too far away");
            this.tasks.clear();
            return null;
        }

        return hit;
    }

    static class ScheduledTask {
        long tick;
        Runnable runnable;

        ScheduledTask(long tick, Runnable runnable) {
            this.tick = tick;
            this.runnable = runnable;
        }
    }
}
