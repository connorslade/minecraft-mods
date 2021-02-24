package com.connorcode.nosebot;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("[*] Starting NoseBot! - By Sigma76");
        System.out.println(" └── https://github.com/Basicprogrammer10");
        getServer().getPluginManager().registerEvents(new ChatEvent(), this);
    }

    @Override
    public void onDisable() {
        System.out.println("[*] Stopping NoseBot :/ - By Sigma76");
    }
}
