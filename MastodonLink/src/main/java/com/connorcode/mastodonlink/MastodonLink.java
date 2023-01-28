package com.connorcode.mastodonlink;

import com.connorcode.mastodonlink.commands.MastodonCommand;
import com.connorcode.mastodonlink.commands.MastodonCompleter;
import com.connorcode.mastodonlink.events.PlayerConnect;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class MastodonLink extends JavaPlugin {
    public static MastodonLink plugin;
    public static Logger logger;
    public static Config config;
    public static Database database;
    public static Mastodon mastodon;
    final File configFile = new File(getDataFolder() + File.separator + "config.yml");

    @Override
    public void onEnable() {
        plugin = this;
        if (!configFile.exists()) saveDefaultConfig();
        logger = getLogger();
        config = new Config(getConfig());
        database = new Database("data.db");

        mastodon = new Mastodon();
        mastodon.auth();
        mastodon.initEventHandler();

        var pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerConnect(), this);

        var mastodonCommand = Objects.requireNonNull(getCommand("mastodon"));
        mastodonCommand.setExecutor(new MastodonCommand());
        mastodonCommand.setTabCompleter(new MastodonCompleter());

        logger.log(Level.INFO, "Started successfully - v" + getDescription().getVersion());
        logger.log(Level.INFO, "Made by Connor Slade | Sigma#8214");
    }

    @Override
    public void onDisable() {
        database.close();
        mastodon.close();
    }
}
