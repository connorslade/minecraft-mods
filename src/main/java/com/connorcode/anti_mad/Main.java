package com.connorcode.anti_mad;

import com.connorcode.anti_mad.Commands.*;
import com.connorcode.anti_mad.Tab.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public final class Main extends JavaPlugin {
    public HashMap<UUID, Integer> taskID = new HashMap<>();
    public HashMap<String, Integer> activity = new HashMap<>();
    public HashMap<UUID, Location> tpHistory = new HashMap<>();
    public ArrayList<UUID> authPlayer = new ArrayList<>();

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[*] Starting Anti Mad! - By Sigma76");

        Objects.requireNonNull(this.getCommand("authme")).setExecutor(new authme(this));
        Objects.requireNonNull(this.getCommand("enchant")).setExecutor(new enchant(this));
        Objects.requireNonNull(this.getCommand("gamemode")).setExecutor(new gamemode(this));
        Objects.requireNonNull(this.getCommand("give")).setExecutor(new give(this));
        Objects.requireNonNull(this.getCommand("heal")).setExecutor(new heal(this));
        Objects.requireNonNull(this.getCommand("kill")).setExecutor(new kill(this));
        Objects.requireNonNull(this.getCommand("list")).setExecutor(new list(this));
        Objects.requireNonNull(this.getCommand("nose")).setExecutor(new nose(this));
        Objects.requireNonNull(this.getCommand("run")).setExecutor(new run(this));
        Objects.requireNonNull(this.getCommand("save")).setExecutor(new save(this));
        Objects.requireNonNull(this.getCommand("seed")).setExecutor(new seed(this));
        Objects.requireNonNull(this.getCommand("stop")).setExecutor(new stop(this));
        Objects.requireNonNull(this.getCommand("task")).setExecutor(new task(this));
        Objects.requireNonNull(this.getCommand("time")).setExecutor(new time(this));
        Objects.requireNonNull(this.getCommand("tp")).setExecutor(new tp(this));
        Objects.requireNonNull(this.getCommand("weather")).setExecutor(new weather(this));
        Objects.requireNonNull(this.getCommand("xp")).setExecutor(new xp(this));

        Objects.requireNonNull(this.getCommand("enchant")).setTabCompleter(new enchant_tc(this));
        Objects.requireNonNull(this.getCommand("gamemode")).setTabCompleter(new gamemode_tc(this));
        Objects.requireNonNull(this.getCommand("give")).setTabCompleter(new give_tc(this));
        Objects.requireNonNull(this.getCommand("kill")).setTabCompleter(new kill_tc(this));
        Objects.requireNonNull(this.getCommand("time")).setTabCompleter(new time_tc(this));
        Objects.requireNonNull(this.getCommand("weather")).setTabCompleter(new weather_tc(this));
        Objects.requireNonNull(this.getCommand("xp")).setTabCompleter(new xp_tc(this));
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[*] Stopping Anti Mad :/ - By Sigma76");
    }
}