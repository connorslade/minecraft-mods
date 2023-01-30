package com.connorcode.nosebot;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[*] Starting NoseBot! - By Sigma76");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "    https://github.com/Basicprogrammer10/NoseBot-Spigot");
        getServer().getPluginManager().registerEvents(new ChatEvent(), this);
    }

    @Override
    public void onDisable() {
        System.out.println("[*] Stopping NoseBot :/ - By Sigma76");
    }
}
