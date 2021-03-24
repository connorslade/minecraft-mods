package com.connorcode.anti_mad.Commands;

import com.connorcode.anti_mad.Main;
import com.connorcode.anti_mad.common;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class tp implements CommandExecutor {
    private final Main plugin;

    public tp(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        final Player player = common.isPlayer(sender);
        assert player != null;

        if (args.length < 1) {
            player.sendMessage(ChatColor.GREEN + "[*] " + ChatColor.RED + "You must Supply a player to teleport to...");
            return true;
        }
        Player p = Bukkit.getServer().getPlayer(args[0]);
        if (p != null) {

            plugin.taskID.put(player.getUniqueId(), plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                plugin.tpHistory.put(player.getUniqueId(), new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch()));
                player.teleport(p);
                player.sendMessage(ChatColor.GREEN + "[*]" + ChatColor.LIGHT_PURPLE + " Teleported to " + p.getName() + " Successfully!");
                plugin.taskID.remove(player.getUniqueId());
            }, 80L));

            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "tellraw " + player.getName() + " [{\"text\":\"[*]\",\"color\":\"green\"},{\"text\":\" You will teleport in 5 seconds.\",\"color\":\"green\"},{\"text\":\" \"},{\"text\":\"[CANCEL]\",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/task cancel\"}}]");

            return true;
        }
        player.sendMessage(ChatColor.RED + "[*] Player \"" + args[0] + "\" Not Found :/");
        return true;
    }
}