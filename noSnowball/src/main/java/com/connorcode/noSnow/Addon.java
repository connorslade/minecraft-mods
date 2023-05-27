package com.connorcode.noSnow;

import com.mojang.logging.LogUtils;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;

public class Addon extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        Modules.get().add(new NoSnowModule());
    }

    @Override
    public String getPackage() {
        return "com.connorcode.noSnow";
    }
}
