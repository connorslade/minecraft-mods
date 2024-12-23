package com.connorcode.fastdoll;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;

import java.io.IOException;
import java.nio.file.Path;

public class FastDoll implements ClientModInitializer {
    public static final String VERSION = "1.9";
    public static final MinecraftClient client = MinecraftClient.getInstance();
    public static final Path configDir = client.runDirectory.toPath().resolve("config");
    public static final Path config = configDir.resolve("fastdoll.json");
    public static boolean enabled = true;

    @Override
    public void onInitializeClient() {
        configDir.toFile().mkdirs();
        if (!config.toFile().exists()) saveConfig();
        else loadConfig();
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
