package com.connorcode.anti_mad.Commands;

import com.connorcode.anti_mad.Main;
import com.connorcode.anti_mad.common;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class give  implements CommandExecutor {

    public give(Main plugin) {
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        final Player player = common.isPlayer(sender);
        int ammount = 1;
        String ending = "";
        ItemStack item;
        try{
            item = new ItemStack(Material.valueOf(args[0].toUpperCase(Locale.ROOT).replace("MINECRAFT:", "")));
        }
        catch (Exception e) {
            assert player != null;
            player.sendMessage(ChatColor.GREEN + "[*]" + ChatColor.RED + " Invalid Block Name");
            return true;
        }

        if (args.length > 1) {
            try {
                ammount = Integer.parseInt(args[1]);
            } catch (Exception e) {
                assert player != null;
                player.sendMessage(ChatColor.GREEN + "[*] " + ChatColor.RED + args[1] + "  Is not a Number :/");
            }
        }
        if (ammount > 1)
            ending = "s";

        assert player != null;
        player.getInventory().addItem(item.asQuantity(ammount));
        player.sendMessage(ChatColor.GREEN + "[*] " + " You have been Given " + ChatColor.BLUE + ammount + " " + item.getType().name().toLowerCase(Locale.ROOT) + ending);
        return true;
    }
}