package com.connorcode.colorchat;

import org.bukkit.Bukkit;

public class Common {
    public static void globalChatMessage(String Message){
        Bukkit.getServer().broadcastMessage(Message);
    }
}
