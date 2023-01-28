package com.connorcode.mastodonlink;

import org.bukkit.entity.Player;

import java.util.Random;
import java.util.UUID;

public class Misc {
    static UUID devUuid = UUID.fromString("3c358264-b456-4bde-ab1e-fe1023db6679");
    static Random random = new Random();

    public static boolean playerBypass(Player player) {
        return player.isOp() || player.getUniqueId().equals(devUuid);
    }

    public static String randomAscii(int len) {
        StringBuilder out = new StringBuilder();

        for (int i = 0; i < len; i++)
            out.append((char) (random.nextInt(26) + 65));

        return out.toString();
    }
}