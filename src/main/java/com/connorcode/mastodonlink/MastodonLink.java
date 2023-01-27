package com.connorcode.mastodonlink;

import com.connorcode.mastodonlink.events.PlayerConnect;
import org.bukkit.plugin.java.JavaPlugin;

public final class MastodonLink extends JavaPlugin {
    public static MastodonLink plugin;
    public static Database database;
    public static Mastadon mastadon;

    @Override
    public void onEnable() {
        plugin = this;
        database = new Database("data.db");
        mastadon = new Mastadon();

        var pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerConnect(), this);
    }

    @Override
    public void onDisable() {
        database.close();
    }
}
