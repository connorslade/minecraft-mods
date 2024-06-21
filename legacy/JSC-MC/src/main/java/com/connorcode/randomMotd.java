package com.connorcode;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class randomMotd implements Listener {
    String firstLine = "\u00A7cJ\u00A7bS\u00A7aC\u00A7d Minecraft\n\u00a77";
    private final String[] motd = {
            "Oh Sid... XRAY hacking again...",
            "Ahhh! the servers at 2TPS... Stop Boat flying!",
            "Diamond Bank Open For Business!",
            "Oh Mr.Binder...",
            "I need more MOTDs",
            "hi guys",
            "$nose",
            "$meme",
            "∑igma On Top",
            "Bamp bamp bamp baaam bump bump bump baaam!",
            "\u00a7l\u00a7oVery Nice",
            "Not again... Connor put down the End Crystals.",
            "h1.hbh7.com:25569 - Never Again...",
            "Lets play some other time... Like Now!",
            "Can you put it on hard mode?",
            "How do you install Paper MC?",
            "Can Mending mend a broken heart? Asking for a friend...",
            "Sid you cant have a pet Glossy black cockatoo",
            "html be much good codeing language",
            "I was mining after class and fell in lava",
            "Try to stay \u00a7l\u00a7oOUT \u00a7rof the lava...",
            "i like \u00a7cC\u00a76O\u00a7eL\u00a7aO\u00a79R",
            "is that a hacke - \u00a7l\u00a7oPROPER TERMINOLOGY!!!",
            "Yay im back!",
            "\u00a7ki hacked the shcool",
            "\u00a7c\u00a7kT\u00a76\u00a7kh\u00a7e\u00a7ki\u00a7a\u00a7ks\u00a71\u00a7k c\u00a7b\u00a7ko\u00a7d\u00a7ko\u00a75\u00a7kl",
            "'Did I crash the server?' --- Yes",
            "You have to stop walking, it made the world 32gb",
            "Minecraft is FOREVER!",
            "Tis' quite colorfull"
    };

    @EventHandler
    public void onPing(ServerListPingEvent event) {
        int i = (int)(Math.random() * motd.length);
        event.setMotd(firstLine + motd[i]);
    }
}