package com.connorcode.spawn;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class Main extends JavaPlugin {
    public static World worldSpawn;

    public HashMap<String, Integer> taskID = new HashMap<>();

    File config = new File(getDataFolder() + File.separator + "config.yml");

    public void onEnable() {
        worldSpawn = getServer().getWorlds().get(0);
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[*] Starting Spawn! - By Sigma76");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "    https://github.com/Basicprogrammer10/Spawn-Plugin");
        if (!config.exists()) {
            getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "[*] Creating config.yml");
            getConfig().addDefault("IsSpawn", Boolean.FALSE);
            getConfig().addDefault("X", 0);
            getConfig().addDefault("Y", 0);
            getConfig().addDefault("Z", 0);
            getConfig().addDefault("Pitch", 0);
            getConfig().addDefault("Yaw", 0);
            getConfig().addDefault("World", "world");
            getConfig().options().copyDefaults(true);
            saveConfig();
        }
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player)sender;
            if (cmd.getName().equalsIgnoreCase("setspawn")) {
                if (!player.isOp()) {
                    player.sendMessage(ChatColor.GREEN + "[*]" + ChatColor.LIGHT_PURPLE + " Nice Try... You need OP");
                    return true;
                }
                player.getWorld().setSpawnLocation(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
                getConfig().set("IsSpawn", Boolean.TRUE);
                getConfig().set("X", player.getLocation().getX());
                getConfig().set("Y", player.getLocation().getY());
                getConfig().set("Z", player.getLocation().getZ());
                getConfig().set("Yaw", player.getLocation().getYaw());
                getConfig().set("Pitch", player.getLocation().getPitch());
                getConfig().set("World", player.getWorld().getName());
                saveConfig();
                reloadConfig();
                player.sendMessage(ChatColor.GREEN + "[*]" + ChatColor.LIGHT_PURPLE + " Spawn Set To " + ChatColor.BLUE + Math.round(player.getLocation().getX()) + ", " + Math.round(player.getLocation().getY()) + ", " + Math.round(player.getLocation().getZ()));
            }
            if (cmd.getName().equalsIgnoreCase("spawn")) {
                if (!getConfig().getBoolean("IsSpawn")) {
                    player.sendMessage(ChatColor.GREEN + "[*]" + ChatColor.LIGHT_PURPLE + " Spawn has not been setup. Use /setspawn");
                    return true;
                }
                if (args.length > 0) {
                    if (!args[0].equalsIgnoreCase("cancel"))
                        return true;
                    getServer().dispatchCommand((CommandSender)getServer().getConsoleSender(), "tellraw " + player.getName() + " [{\"text\":\"[*]\",\"color\":\"green\"},{\"text\":\" Request Cancelled\",\"color\":\"red\"},{\"text\":\" \"},{\"text\":\"[TO SPAWN]\",\"color\":\"blue\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/spawn\"}}]");
                    getServer().getScheduler().cancelTask((Integer) taskID.get(player.getName()));
                    return true;
                }
                if (getConfig().getBoolean("IsSpawn")) {
                    taskID.put(player.getName(), getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                        public void run() {
                            player.teleport(new Location(getServer().getWorld(Objects.<String>requireNonNull(getConfig().getString("World"))), getConfig().getDouble("X"), getConfig().getDouble("Y"), getConfig().getDouble("Z"), getConfig().getInt("Yaw"), getConfig().getInt("Pitch")));
                            player.sendMessage(ChatColor.GREEN + "[*]" + ChatColor.LIGHT_PURPLE + " Welcome to Spawn " + ChatColor.LIGHT_PURPLE + player.getName() + "!");
                        }
                    }, 100L));

                    getServer().dispatchCommand((CommandSender)getServer().getConsoleSender(), "tellraw " + player.getName() + " [{\"text\":\"[*]\",\"color\":\"green\"},{\"text\":\" You will teleport in 5 seconds.\",\"color\":\"green\"},{\"text\":\" \"},{\"text\":\"[CANCEL]\",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/spawn cancel\"}}]");
                    return true;
                }
            }
        }
        return true;
    }

    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[*] Stopping Spawn :/ - By Sigma76");
    }
}