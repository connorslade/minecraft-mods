package com.connorcode.anti_mad.Commands;

import com.connorcode.anti_mad.Main;
import com.connorcode.anti_mad.common;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class save implements CommandExecutor {

    public save(Main plugin) {
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        final Player player = common.isPlayer(sender);
        assert player != null;

        player.sendMessage(ChatColor.GREEN + "[*]" + ChatColor.LIGHT_PURPLE + " Starting World save...");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "save-all");
        player.sendMessage(ChatColor.GREEN + "[*]" + ChatColor.GREEN + " Save Complete");
        return true;
    }
}