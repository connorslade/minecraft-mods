package com.connorcode.nosebot;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;

public class Nose {
    public static void sendNose() {
        Server server = Bukkit.getServer();

        server.broadcastMessage(ChatColor.BLUE +
                "   / \\__\n" +
                "  (    @\\___\n" +
                "  /         O\n" +
                " /   (_____/\n" +
                "/_____/   U");

        server.broadcastMessage(ChatColor.GREEN + "N O S E !");
    }
}
