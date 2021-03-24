package com.connorcode.anti_mad.Commands;

import com.connorcode.anti_mad.Main;
import com.connorcode.anti_mad.common;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class time implements CommandExecutor {

    public time(Main plugin) {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        final Player player = common.isPlayer(sender);
        assert player != null;
        if (args.length < 1) {
            player.sendMessage(ChatColor.GREEN + "[*]" + ChatColor.RED + " Supply a time to set to... (Day | Noon | Night)");
            return true;
        }
        if (args[0].equalsIgnoreCase("day")) {
            player.sendMessage(ChatColor.GREEN + "[*]" + ChatColor.GREEN + " Setting Time to " + ChatColor.BLUE + "Day");
            player.getWorld().setTime(1000);
            return true;
        }
        if (args[0].equalsIgnoreCase("noon")) {
            player.sendMessage(ChatColor.GREEN + "[*]" + ChatColor.GREEN + " Setting Time to " + ChatColor.BLUE + "Noon");
            player.getWorld().setTime(6000);
            return true;
        }
        if (args[0].equalsIgnoreCase("night")) {
            player.sendMessage(ChatColor.GREEN + "[*]" + ChatColor.GREEN + " Setting Time to " + ChatColor.BLUE + "Night");
            player.getWorld().setTime(13000);
            return true;
        }
        player.sendMessage(ChatColor.GREEN + "[*]" + ChatColor.RED + " Unknown time of " + args[0]);
        return true;
    }
}