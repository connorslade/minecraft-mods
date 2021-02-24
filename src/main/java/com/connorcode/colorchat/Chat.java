package com.connorcode.colorchat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Arrays;

public class Chat implements Listener {

    String[] codes = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", /*Modifiers*/ "k", "l", "m", "n", "o"};
    String colorCodeChar = "&";

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        String playerName = e.getPlayer().getDisplayName();
        String Message = e.getMessage();

        if (Message.contains(colorCodeChar)){
            e.setCancelled(true);
            Message = Message.replace(colorCodeChar, "§");
            Common.globalPlayerChatMessahe(playerName, Message);
        }
        else if (Message.startsWith(">")) {
            e.setCancelled(true);
            Common.globalChatMessage(String.format("<%s> %s%s", playerName, ChatColor.GREEN, Message));
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        String Message = e.getMessage();
        Player player = e.getPlayer();

        if (Message.startsWith("/chatcolors") || Message.startsWith("/colorchat:chatcolors")) {
            e.setCancelled(true);
            StringBuilder working = new StringBuilder();
            for (String code : codes) {
                working.append(String.format("§%s&%s§f | ", code, code));
            }
            player.sendMessage("§e--------- §fColor Chat §e---------------------------------");
            player.sendMessage(String.valueOf(working).substring(0, working.length() - 3));
            player.sendMessage("");
            player.sendMessage("> &4H&6E&eL&2L&3O");
            player.sendMessage("<Steve> §4H§6E§eL§2L§3O");
            player.sendMessage("§e-----------------------------------------------------");
        }
    }
}
