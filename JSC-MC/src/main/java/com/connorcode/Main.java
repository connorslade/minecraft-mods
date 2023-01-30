package com.connorcode;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[*] Starting JSC-MC! - By Sigma76");
        getServer().getPluginManager().registerEvents(new randomMotd(), this);
        getServer().getPluginManager().registerEvents(new playerJoinLeave(), this);
        tps.initTpsCheck(this);
        try {
            discordHooks.onStart();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        try {
            discordHooks.onStop();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[*] Stopping JSC-MC");
    }
}
