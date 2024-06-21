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

public class stop implements CommandExecutor {
    private final Main plugin;

    public stop(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        final Player player = common.isPlayer(sender);

        if (args.length < 1) {
            assert player != null;
            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "tellraw " + player.getName() + " [{\"text\":\"[*]\",\"color\":\"green\"},{\"text\":\" Are you sure you want to Stop the server?\",\"color\":\"green\"},{\"text\":\" \"},{\"text\":\"[YES] \",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/stop yes\"}},{\"text\":\"[NO]\",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/stop no\"}}]");
            return true;
        }
        if (args[0].equalsIgnoreCase("yes")) {

            plugin.activity.put("shutdown", plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getServer().shutdown(), 80L));

            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(ChatColor.GREEN + "[*]" + ChatColor.RED + " Stopping Server In 5 Seconds...");
            }

            return true;
        }
        if (args[0].equalsIgnoreCase("no")) {
            assert player != null;
            player.sendMessage(ChatColor.GREEN + "[*]" + ChatColor.GREEN + " That was a close one!");
            try {
                plugin.getServer().getScheduler().cancelTask(plugin.activity.get("shutdown"));
            } catch (Exception ignored) {

            }
            return true;
        }
        return true;
    }
}