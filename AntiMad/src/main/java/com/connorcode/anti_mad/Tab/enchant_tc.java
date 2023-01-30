package com.connorcode.anti_mad.Tab;

import com.connorcode.anti_mad.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class enchant_tc implements TabCompleter {

    public enchant_tc(Main plugin) {
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String commandLabel, String[] args) {
        ArrayList<String> ench = new ArrayList<>();

        for (Enchantment enchantment: Enchantment.values())
            ench.add(enchantment.getKey().asString().toLowerCase(Locale.ROOT).replace("minecraft:", ""));

        if (args.length > 1) {
            return new ArrayList<>();
        }
        return ench;
    }
}