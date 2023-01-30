package com.connorcode.anti_mad.Commands;

import com.connorcode.anti_mad.Main;
import com.connorcode.anti_mad.common;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class task implements CommandExecutor {
    private final Main plugin;

    public task(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        final Player player = common.isPlayer(sender);
        assert player != null;

        if (args.length < 1)
            return true;

        try {
            plugin.getServer().getScheduler().cancelTask(plugin.taskID.get(player.getUniqueId()));
            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "tellraw " + player.getName() + " [{\"text\":\"[*]\",\"color\":\"green\"},{\"text\":\" Request Cancelled\",\"color\":\"red\"},{\"text\":\" \"}]");
            return true;
        } catch (Exception e) {
            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "tellraw " + player.getName() + " [{\"text\":\"[*]\",\"color\":\"green\"},{\"text\":\" No Request Pending\",\"color\":\"red\"}]");
        }
        return true;
    }
}