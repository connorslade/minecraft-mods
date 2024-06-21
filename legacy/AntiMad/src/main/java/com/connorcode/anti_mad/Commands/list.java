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

public class list implements CommandExecutor {
    private final Main plugin;

    public list(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        final Player player = common.isPlayer(sender);

        StringBuilder onlinePlayers = new StringBuilder();
        int numOfOnlinePlayers = 0;
        for (Player p : Bukkit.getOnlinePlayers()) {
            numOfOnlinePlayers++;
            onlinePlayers.append("   (").append(numOfOnlinePlayers).append(") ").append(p.getName()).append("\n");
        }
        assert player != null;
        player.sendMessage(ChatColor.GREEN + "[*] " + ChatColor.LIGHT_PURPLE + numOfOnlinePlayers + " of " + plugin.getServer().getMaxPlayers() + " Players Online\n" + ChatColor.YELLOW + onlinePlayers);
        return true;
    }
}