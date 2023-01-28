package com.connorcode.mastodonlink;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    public final int verifyCodeLength;
    public final Bot bot;

    Config(FileConfiguration config) {
        verifyCodeLength = config.getInt("verifyCodeLength", 6);
        bot = new Bot(config);
    }

    public record Bot(String address) {
        Bot(FileConfiguration config) {
            this(config.getString("bot.address"));
        }
    }
}
