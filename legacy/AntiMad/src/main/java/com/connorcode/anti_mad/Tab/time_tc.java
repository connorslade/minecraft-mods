package com.connorcode.anti_mad.Tab;

import com.connorcode.anti_mad.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class time_tc implements TabCompleter {

    public time_tc(Main plugin) {
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, Command cmd, @NotNull String commandLabel, String[] args) {
        ArrayList<String> time = new ArrayList<>(Arrays.asList(
                "day",
                "noon",
                "night"
        ));

        Collections.sort(time);
        return time;
    }
}