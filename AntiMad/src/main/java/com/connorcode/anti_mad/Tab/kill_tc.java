package com.connorcode.anti_mad.Tab;

import com.connorcode.anti_mad.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class kill_tc implements TabCompleter {

    public kill_tc(Main plugin) {
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String commandLabel, String[] args) {
        return new ArrayList<>();
    }
}