package com.connorcode.colorchat;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[*] Starting ColorChat! - By Sigma76");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "    https://github.com/Basicprogrammer10");
        getServer().getPluginManager().registerEvents(new Chat(), this);
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[*] Stopping Color Chat :/ - By Sigma76");
    }
}
