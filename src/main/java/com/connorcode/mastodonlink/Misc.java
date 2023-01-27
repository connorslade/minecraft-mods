package com.connorcode.mastodonlink;

import org.bukkit.entity.Player;

import java.util.UUID;

public class Misc {
    static UUID devUuid = UUID.fromString("3c358264-b456-4bde-ab1e-fe1023db6679");

    public static boolean playerBypass(Player player) {
        return player.isOp() || player.getUniqueId().equals(devUuid);
    }
}
