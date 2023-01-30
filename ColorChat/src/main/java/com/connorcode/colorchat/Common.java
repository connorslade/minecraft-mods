package com.connorcode.colorchat;

import org.bukkit.Bukkit;

public class Common {
    public static void globalChatMessage(String Message){
        Bukkit.getServer().broadcastMessage(Message);
    }
    public static void globalPlayerChatMessahe(String PlayerName, String Message){
        Bukkit.getServer().broadcastMessage(String.format("<%s> %s", PlayerName, Message));
    }
}
