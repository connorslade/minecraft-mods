package com.connorcode.anti_mad.Commands;

import com.connorcode.anti_mad.Main;
import com.connorcode.anti_mad.common;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class gamemode implements CommandExecutor {

    public gamemode(Main plugin) {
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        final Player player = common.isPlayer(sender);
        assert player != null;

        if (args.length < 1) {
            player.sendMessage(ChatColor.LIGHT_PURPLE + "[*] " + ChatColor.RED + "You must Supply a Gamemode.");
            return true;
        }
        if (args[0].equalsIgnoreCase("creative")) {
            player.setGameMode(GameMode.CREATIVE);
            player.sendMessage(ChatColor.GREEN + "[*]" + ChatColor.LIGHT_PURPLE + " Gamemode set to Creative!");
            return true;
        }
        if (args[0].equalsIgnoreCase("survival")) {
            player.setGameMode(GameMode.SURVIVAL);
            player.sendMessage(ChatColor.GREEN + "[*]" + ChatColor.LIGHT_PURPLE + " Gamemode set to Survival!");
            return true;
        }
        if (args[0].equalsIgnoreCase("spectator")) {
            player.setGameMode(GameMode.SPECTATOR);
            player.sendMessage(ChatColor.GREEN + "[*]" + ChatColor.LIGHT_PURPLE + " Gamemode set to Spectator!");
            return true;
        }
        player.sendMessage(ChatColor.GREEN + "[*]" + ChatColor.RED + " Unknown Gamemode: " + args[0].toLowerCase(Locale.ROOT));
        return true;
    }
}