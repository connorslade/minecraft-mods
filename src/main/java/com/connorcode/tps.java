package com.connorcode;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.IOException;

public class tps implements Runnable
{
    public static boolean tpsDropSent = false;
    public static int TICK_COUNT= 0;
    public static long[] TICKS= new long[600];

    public static double getTPS()
    {
        return getTPS(100);
    }

    public static double getTPS(int ticks)
    {
        if (TICK_COUNT< ticks) {
            return 20.0D;
        }
        int target = (TICK_COUNT- 1 - ticks) % TICKS.length;
        long elapsed = System.currentTimeMillis() - TICKS[target];

        return ticks / (elapsed / 1000.0D);
    }

    public static long getElapsed(int tickID)
    {
        long time = TICKS[(tickID % TICKS.length)];
        return System.currentTimeMillis() - time;
    }

    public void run()
    {
        TICKS[(TICK_COUNT% TICKS.length)] = System.currentTimeMillis();
        TICK_COUNT+= 1;
    }

    public static void initTpsCheck(Plugin plugin) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                @Override
                public void run() {
                    double tps = getTPS();
                    if (tps <= 15 && !tpsDropSent) {
                        tpsDropSent = true;
                        try {
                            discordHooks.onTpsDrop(tps);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if (tps >= 15 && tpsDropSent) {
                        tpsDropSent = false;
                    }
                }
            }, 100, 100);
    }
}