package com.connorcode.fastdoll;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

import static com.connorcode.fastdoll.FastDoll.VERSION;

public class ConfigScreen extends Screen implements Renderable {
    Screen _super;

    protected ConfigScreen(Screen _super) {
        super(Component.nullToEmpty("FastDoll Config"));
        this._super = _super;
    }

    private String getState() {
        return "Fast Doll: " + (FastDoll.enabled ? "§aEnabled" : "§cDisabled");
    }

    @Override
    protected void init() {
        this.addRenderableWidget(Button.builder(Component.nullToEmpty(getState()), button -> {
            FastDoll.enabled ^= true;
            button.setMessage(Component.nullToEmpty(getState()));
        }).pos(width / 2 - 75, height / 3 - 10).build());
    }


    public void extractRenderState(@NonNull GuiGraphicsExtractor drawContext, int mouseX, int mouseY, float delta) {
        super.extractRenderState(drawContext, mouseX, mouseY, delta);

        final var text = "§l§nFastDoll v" + VERSION;
        drawContext.text(font, text, (int) (width / 2f - font.width(text) / 2f),
                (int) (height / 3f - font.lineHeight / 2f - 25), 0xffffffff, false);
    }

    @Override
    public void onClose() {
        assert this.minecraft != null;
        this.minecraft.setScreen(_super);
    }
}
