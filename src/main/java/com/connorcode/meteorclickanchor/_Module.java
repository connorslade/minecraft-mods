package com.connorcode.meteorclickanchor;

import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Module;

public class _Module extends Module {
    public _Module() {
        super(Categories.Combat, "Click Anchor", "Automatically charges and detonates respawn anchors when places.");
    }
}
