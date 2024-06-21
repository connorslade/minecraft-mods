package com.connorcode.anti_mad;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class common {
    public static Player isPlayer(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.GREEN + "[*]" + ChatColor.RED + " You must be a player :/");
            return null;
        }
        return (Player) sender;
    }
}
