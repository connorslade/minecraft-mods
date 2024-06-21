package com.connorcode.meteorclickanchor;

import meteordevelopment.meteorclient.addons.MeteorAddon;

public class Main extends MeteorAddon {

    @Override
    public void onInitialize() {
        System.out.println("Meteor Click Anchor initialized!");
    }

    @Override
    public String getPackage() {
        return "com.connorcode.meteorclickanchor";
    }
}
