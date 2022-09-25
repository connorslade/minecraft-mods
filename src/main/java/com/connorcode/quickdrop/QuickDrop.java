package com.connorcode.quickdrop;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static com.mojang.brigadier.arguments.DoubleArgumentType.doubleArg;
import static com.mojang.brigadier.arguments.DoubleArgumentType.getDouble;

public class QuickDrop implements ModInitializer {
    public static final File configFile = new File("config/QuickDrop/config.nbt");
    public static double multiplier = 1.0;

    @Override
    public void onInitialize() {
        // Load Config on Startup
        try {
            loadConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Init Commands
        CommandRegistrationCallback.EVENT.register(
                (dispatcher, registryAccess, environment) -> dispatcher.register(CommandManager.literal("quickdrop")
                        .requires(src -> src.hasPermissionLevel(4))
                        .then(CommandManager.literal("set")
                                .then(CommandManager.argument("multiplier", doubleArg())
                                        .executes(ctx -> {
                                            multiplier = getDouble(ctx, "multiplier");
                                            try {
                                                saveConfig();
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }
                                            return 0;
                                        })))
                        .then(CommandManager.literal("status")
                                .executes(ctx -> {
                                    Objects.requireNonNull(ctx.getSource()
                                                    .getPlayer())
                                            .sendMessage(Text.of(String.format(
                                                    "Quickdrop multiplier is %d%%",
                                                    (int) (multiplier * 100))));
                                    return 0;
                                }))));
    }

    void loadConfig() throws IOException {
        if (!configFile.exists()) return;
        NbtCompound nbt = Objects.requireNonNull(NbtIo.read(configFile));
        multiplier = nbt.getDouble("multiplier");
    }

    void saveConfig() throws IOException {
        if (!configFile.exists() && (!configFile.getParentFile()
                .mkdirs() ||
                !configFile.createNewFile())) {
            throw new RuntimeException("Error Making Config File");
        }
        NbtCompound nbt = new NbtCompound();
        nbt.putDouble("multiplier", multiplier);
        NbtIo.write(nbt, configFile);
    }
}
