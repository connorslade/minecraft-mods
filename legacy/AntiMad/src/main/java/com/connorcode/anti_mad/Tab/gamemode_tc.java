package com.connorcode.anti_mad.Tab;

import com.connorcode.anti_mad.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class gamemode_tc implements TabCompleter {

    public gamemode_tc(Main plugin) {
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String commandLabel, String[] args) {
        ArrayList<String> gamemodes = new ArrayList<>(Arrays.asList(
                "creative",
                "survival",
                "spectator"
        ));
        if (args.length == 1)
            return gamemodes;
        return new ArrayList<>();
    }
}