package com.connorcode.meteorclickanchor;

import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Modules;

public class Main extends MeteorAddon {

    @Override
    public void onInitialize() {
        Modules.get().add(new _Module());
    }

    @Override
    public String getPackage() {
        return "com.connorcode.meteorclickanchor";
    }
}
