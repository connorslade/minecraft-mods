package com.connorcode.anti_mad.Tab;

import com.connorcode.anti_mad.Main;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class give_tc implements TabCompleter {

    public give_tc(Main plugin) {
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String commandLabel, String[] args) {
        ArrayList<String> mats = new ArrayList<>();

        if (args.length == 1){
            for (Material material: Material.values())
                mats.add("minecraft:" + material.name().toLowerCase(Locale.ROOT));
            return mats;
        }
        if (args.length == 2){
            return new ArrayList<>(Arrays.asList("1", "64"));
        }
        return null;
    }
}