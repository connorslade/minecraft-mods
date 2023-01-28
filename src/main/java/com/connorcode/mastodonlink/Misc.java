package com.connorcode.mastodonlink;

import org.bukkit.entity.Player;

import java.net.URI;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static com.connorcode.mastodonlink.MastodonLink.config;

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

    public static String mastodonAcct(String acct, String uri) {
        var host = URI.create(uri).getHost();
        if (acct.contains("@")) return acct;
        return acct + "@" + host;
    }

    public static Optional<String> findCode(String raw) {
        var len = config.verifyCodeLength;
        for (int i = 0; i < raw.length() - len; i++) {
            var sub = raw.substring(i, i + len);
            if (sub.matches("[A-Z]{6}")) return Optional.of(sub);
        }

        return Optional.empty();
    }
}