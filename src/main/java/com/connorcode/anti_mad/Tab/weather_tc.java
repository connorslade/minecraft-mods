package com.connorcode.anti_mad.Tab;

import com.connorcode.anti_mad.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class weather_tc implements TabCompleter {

    public weather_tc(Main plugin) {
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, Command cmd, @NotNull String commandLabel, String[] args) {
        ArrayList<String> weather = new ArrayList<>(Arrays.asList(
                "clear",
                "rain",
                "thunder"
        ));

        if (args.length == 1) {
            return weather;
        }
        return new ArrayList<>();
    }
}