package com.connorcode.anti_mad.Commands;

import com.connorcode.anti_mad.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class enchant implements CommandExecutor {

    public enchant(Main plugin) {
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.GREEN + "[*]" + ChatColor.RED + " You must be a player :/");
            return true;
        }
        final Player player = (Player) sender;
        ItemStack item = player.getItemInHand();
        int level = 1;
        if (item.getType() == Material.AIR) {
            player.sendMessage(ChatColor.GREEN + "[*]" + ChatColor.RED + " You are not holding anything :/  ");
            return true;
        }
        NamespacedKey key = NamespacedKey.minecraft(args[0].toLowerCase(Locale.ROOT).replace("minecraft:", ""));
        Enchantment enchant = Enchantment.getByKey(key);
        try{
            level = Integer.parseInt(args[1]);
        } catch (Exception ignored) { }
        if (enchant == null) {
            player.sendMessage(ChatColor.GREEN + "[*]" + ChatColor.RED + " Unknown Enchantment");
            return true;
        }
        item.addUnsafeEnchantment(enchant, level);
        player.sendMessage(ChatColor.GREEN + "[*] " + ChatColor.BLUE + args[0].toLowerCase(Locale.ROOT).replace("minecraft:", "") + " " + level + ChatColor.GREEN + " has been applied to " + ChatColor.BLUE + item.getI18NDisplayName());

        return true;
    }
}