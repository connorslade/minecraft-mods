package com.connorcode.mastodonlink.events;

import com.connorcode.mastodonlink.MastodonLink;
import com.connorcode.mastodonlink.Misc;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import static com.connorcode.mastodonlink.Consts.baseStyle;

public class PlayerConnect implements Listener {
    @EventHandler
    void onPlayerLogin(PlayerLoginEvent e) {
        if (Misc.playerBypass(e.getPlayer())) {
            e.getPlayer().sendMessage(Component.text("MastodonLink: You bypassed the login check!"));
            // return;
        }

        if (!MastodonLink.database.isPlayerLinked(e.getPlayer())) {
            var pendingOption = MastodonLink.database.isPlayerPending(e.getPlayer());
            var pending = pendingOption.orElseGet(() -> MastodonLink.database.createAuthCode(e.getPlayer()));

            e.disallow(PlayerLoginEvent.Result.KICK_OTHER,
                    Component.text("You must link your Mastodon account to play on this server!")
                            .style(baseStyle.color(NamedTextColor.GREEN))
                            .appendNewline()
                            .append(Component.text("DM ").style(baseStyle.color(NamedTextColor.LIGHT_PURPLE)))
                            .append(Component.text(MastodonLink.config.bot.address())
                                    .style(baseStyle.color(NamedTextColor.AQUA)))
                            .append(Component.text(" with your code ")
                                    .style(baseStyle.color(NamedTextColor.LIGHT_PURPLE)))
                            .append(Component.text(pending).style(baseStyle.color(NamedTextColor.AQUA))));
        }
    }
}
