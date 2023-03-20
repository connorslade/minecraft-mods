package com.connorcode.fastdoll;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;

import java.io.File;
import java.io.IOException;

public class FastDoll implements ClientModInitializer {
    public static final String VERSION = "1.0";
    public static final MinecraftClient client = MinecraftClient.getInstance();
    public static final File config = client.runDirectory.toPath().resolve("config/fast-doll.nbt").toFile();
    public static boolean enabled = true;

    @Override
    public void onInitializeClient() {
        if (!config.exists()) {
            saveConfig();
            return;
        }

        loadConfig();
    }

    void saveConfig() {
        var out = new NbtCompound();
        out.putString("info", "fast-doll v" + VERSION);
        out.putBoolean("enabled", enabled);
        try {
            NbtIo.write(out, config);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void loadConfig() {
        NbtCompound in;
        try {
            in = NbtIo.read(config);
            assert in != null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        enabled = !in.contains("enabled") || in.getBoolean("enabled");
    }
}
