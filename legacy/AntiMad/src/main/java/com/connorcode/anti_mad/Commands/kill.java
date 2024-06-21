package com.connorcode.anti_mad.Commands;

import com.connorcode.anti_mad.Main;
import com.connorcode.anti_mad.common;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class kill implements CommandExecutor {
    private final Main plugin;

    public kill(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        final Player player = common.isPlayer(sender);
        assert player != null;

        if (args.length < 1) {
            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "tellraw " + player.getName() + " [{\"text\":\"[*]\",\"color\":\"green\"},{\"text\":\" Are you sure you want to die?\",\"color\":\"green\"},{\"text\":\" \"},{\"text\":\"[YES] \",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/kill yes\"}},{\"text\":\"[NO]\",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/kill no\"}}]");
            return true;
        }
        if (args[0].equalsIgnoreCase("yes")) {
            player.sendMessage(ChatColor.GREEN + "[*]" + ChatColor.RED + " Goodbye!");
            player.setHealth(0.0);
            return true;
        }
        if (args[0].equalsIgnoreCase("no")) {
            player.sendMessage(ChatColor.GREEN + "[*]" + ChatColor.GREEN + " You are safe :P");
            return true;
        }
        return true;
    }
}