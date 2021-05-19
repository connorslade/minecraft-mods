package com.connorcode;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;

public class playerJoinLeave implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) throws IOException {
        Player p = e.getPlayer();
        discordHooks.onPlayerJoin(p.getName(), p.getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) throws IOException {
        Player p = e.getPlayer();
        discordHooks.onPlayerLeave(p.getName(), p.getUniqueId());
    }
}