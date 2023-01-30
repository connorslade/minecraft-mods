package com.connorcode.anti_mad.Commands;

import com.connorcode.anti_mad.Main;
import com.connorcode.anti_mad.common;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class heal implements CommandExecutor {

    public heal(Main plugin) {
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        final Player player = common.isPlayer(sender);
        assert player != null;
        player.setHealth(20d);
        player.setFoodLevel(20);
        player.setSaturation(20f);
        player.sendMessage(ChatColor.GREEN + "[*]" + " You are now at Full Health");
        return true;
    }
}