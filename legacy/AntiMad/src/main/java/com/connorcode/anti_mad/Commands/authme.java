package com.connorcode.anti_mad.Commands;

import com.connorcode.anti_mad.Main;
import com.connorcode.anti_mad.common;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class authme implements CommandExecutor {
    private final Main plugin;

    public authme(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        final Player player = common.isPlayer(sender);
        assert player != null;

        if (args.length < 1){
            return true;
        }
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String[] code = sdf.format(cal.getTime()).split(":");
        int value = Integer.parseInt(code[0]) * Integer.parseInt(code[1]);
        String valueString = String.valueOf(value);
        int hash = 7;
        for (int i = 0; i < valueString.length(); i++) {
            hash = hash * 7919 + valueString.charAt(i);
        }
        if (args[0].equals(String.valueOf(hash))){
            player.sendMessage(ChatColor.GREEN + "[*] ACCESS GRANTED");
            plugin.authPlayer.add(player.getUniqueId());
        }
        return true;
    }
}