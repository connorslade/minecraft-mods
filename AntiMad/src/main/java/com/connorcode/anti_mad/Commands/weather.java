package com.connorcode.anti_mad.Commands;

import com.connorcode.anti_mad.Main;
import com.connorcode.anti_mad.common;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class weather implements CommandExecutor {

    public weather(Main plugin) {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        final Player player = common.isPlayer(sender);
        assert player != null;

        if (args.length < 1) {
            player.sendMessage(ChatColor.GREEN + "[*]" + ChatColor.RED + " Please supply weather to set to...");
            return true;
        }
        if (args[0].equalsIgnoreCase("clear")) {
            player.getWorld().setStorm(false);
            player.getWorld().setThundering(false);
            player.sendMessage(ChatColor.GREEN + "[*]" + ChatColor.GREEN + " Weather set to clear");
            return true;
        }
        if (args[0].equalsIgnoreCase("rain")) {
            player.getWorld().setStorm(true);
            player.getWorld().setThundering(false);
            player.sendMessage(ChatColor.GREEN + "[*]" + ChatColor.GREEN + " Weather set to rain");
            return true;
        }
        if (args[0].equalsIgnoreCase("thunder")) {
            player.getWorld().setStorm(false);
            player.getWorld().setThundering(true);
            player.sendMessage(ChatColor.GREEN + "[*]" + ChatColor.GREEN + " Weather set to thunder");
            return true;
        }
        player.sendMessage(ChatColor.GREEN + "[*]" + ChatColor.RED + " Unknown weather: " + args[0]);
        return true;
    }
}