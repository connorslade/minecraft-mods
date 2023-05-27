package com.connorcode.noSnow;

import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;

import java.util.ArrayList;
import java.util.Objects;

public class NoSnowModule extends Module {
    public NoSnowModule() {
        super(Categories.Render, "No Snowball", "Prevents snowballs from being processed or rendered on the client.");
    }

    @Override
    public void onActivate() {
        var balls = new ArrayList<Integer>();
        Objects.requireNonNull(this.mc.world).getEntities().forEach(e -> {
            if (e.getType() != EntityType.SNOWBALL) return;
            balls.add(e.getId());
        });
        for (var i : balls) this.mc.world.removeEntity(i, Entity.RemovalReason.DISCARDED);
    }

    @EventHandler
    void onPacket(PacketEvent.Receive packet) {
        if (!isActive()) return;
        if (!(packet.packet instanceof EntitySpawnS2CPacket entitySpawnS2CPacket)) return;
        if (entitySpawnS2CPacket.getEntityType() != EntityType.SNOWBALL) return;
        packet.cancel();
    }
}
