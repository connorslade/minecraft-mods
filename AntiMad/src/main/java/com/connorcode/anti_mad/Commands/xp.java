package com.connorcode.anti_mad.Commands;

import com.connorcode.anti_mad.Main;
import com.connorcode.anti_mad.common;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class xp implements CommandExecutor {

    public xp(Main plugin) {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        final Player player = common.isPlayer(sender);
        assert player != null;

        int xpToGive;
        if (args.length < 2) {
            player.sendMessage(ChatColor.GREEN + "[*]" + ChatColor.RED + " Incorrect command usage... Use /xp (add | set) <Number> (Levels | Points)");
            return true;
        }
        try {
            xpToGive = Integer.parseInt(args[1]);
        } catch (Exception e) {
            player.sendMessage(ChatColor.GREEN + "[*]" + ChatColor.RED + " You must supply a number");
            return true;
        }
        if (args[0].equalsIgnoreCase("add")) {
            player.sendMessage(ChatColor.GREEN + "[*]" + ChatColor.GREEN + " You have been given " + args[1] + " XP");
            player.giveExp(xpToGive, false);
            return true;
        }
        return true;
    }
}