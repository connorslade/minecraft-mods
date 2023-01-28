package com.connorcode.mastodonlink.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static com.connorcode.mastodonlink.MastodonLink.database;

public class MastodonCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 1) {
            sender.sendMessage("Usage: /mastodon <player>");
            return true;
        }

        var player = sender.getServer().getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage("Player not found!");
            return true;
        }

        var mastodon = database.getMastodon(player);
        if (mastodon.isEmpty()) {
            sender.sendMessage("Player is not linked!");
            return true;
        }

        sender.sendMessage(player.getName() + " is linked to " + mastodon.get());
        return true;
    }
}
