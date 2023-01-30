package com.connorcode.lag;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[*] Starting Lag Display! - By Sigma76");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "    https://github.com/Basicprogrammer10/");

        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Lag(), 100L, 1L);
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player)sender;
            if (cmd.getName().equalsIgnoreCase("lag")) {
                DecimalFormat df = new DecimalFormat("#.#");
                df.setRoundingMode(RoundingMode.CEILING);
                double tps = Lag.getTPS();
                String color = colorForTps(tps);
                getServer().dispatchCommand(getServer().getConsoleSender(), "tellraw " + player.getName() + " [{\"text\":\"[*] \",\"color\":\"light_purple\"},{\"text\":\"Server TPS:\",\"color\":\"gold\"},{\"text\":\" " + df.format(tps) + "\",\"color\":\"" + color + "\"},{\"text\":\" (" + df.format((tps / 20.0D) * 100.0D) + "%)\",\"color\":\"blue\"}]");
                getServer().dispatchCommand(getServer().getConsoleSender(), "tellraw " + player.getName() + " [\"\",{\"text\":\"[*] \",\"color\":\"light_purple\"}" + getBoxesForTps(tps) + "]");
            }
        }
        return true;
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[*] Stopping Lag Display :/ - By Sigma76");
    }

    private String colorForTps(double tps){
        if (tps <= 5)
            return "red";
        if (tps <= 10)
            return "gold";
        if (tps <= 15)
            return "yellow";
        if (tps <= 20)
            return "green";
        return "light_purple";
    }

    private String getBoxesForTps(double tps){
        StringBuilder boxes = new StringBuilder("");
        for (int i = 0; i < (int)tps; i++){
            boxes.append(",{\"text\":\"▌\",\"color\":\"").append(colorForTps(i)).append("\"}");
        }
        return boxes.toString();
    }
}
