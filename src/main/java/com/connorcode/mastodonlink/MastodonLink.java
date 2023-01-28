package com.connorcode.mastodonlink;

import com.connorcode.mastodonlink.events.PlayerConnect;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class MastodonLink extends JavaPlugin {
    public static MastodonLink plugin;
    public static Logger logger;
    public static Config config;
    public static Database database;
    public static Mastodon mastadon;
    final File configFile = new File(getDataFolder() + File.separator + "config.yml");

    @Override
    public void onEnable() {
        plugin = this;
        if (!configFile.exists()) saveDefaultConfig();
        logger = getLogger();
        config = new Config(getConfig());
        database = new Database("data.db");

        mastadon = new Mastodon();
        mastadon.auth();
        mastadon.initEventHandler();

        var pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerConnect(), this);

        logger.log(Level.INFO, "Started successfully");
    }

    @Override
    public void onDisable() {
        database.close();
    }
}
