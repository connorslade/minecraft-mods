package com.connorcode.mastodonlink.events;

import com.connorcode.mastodonlink.MastodonLink;
import com.connorcode.mastodonlink.Misc;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerConnect implements Listener {
    @EventHandler
    void onPlayerLogin(PlayerLoginEvent e) {
        if (Misc.playerBypass(e.getPlayer())) {
            e.getPlayer().sendMessage(Component.text("MastodonLink: You bypassed the login check!"));
//            return;
        }

        var uuid = e.getPlayer().getUniqueId();
        if (!MastodonLink.database.isPlayerLinked(uuid)) {
            var pending = MastodonLink.database.isPlayerPending(uuid);
            if (pending) e.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("You are pending verification!"));
            else e.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("You must link your Mastodon account to play on this server!"));
        }
    }
}
