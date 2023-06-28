package com.connorcode.fastdoll;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import static com.connorcode.fastdoll.FastDoll.VERSION;

public class ConfigScreen extends Screen {
    Screen _super;

    protected ConfigScreen(Screen _super) {
        super(Text.of("FastDoll Config"));
        this._super = _super;
    }

    private String getState() {
        return "Fast Doll: " + (FastDoll.enabled ? "§aEnabled" : "§cDisabled");
    }

    @Override
    protected void init() {
        this.addDrawableChild(ButtonWidget.builder(Text.of(getState()), button -> {
            FastDoll.enabled ^= true;
            button.setMessage(Text.of(getState()));
        }).position(width / 2 - 75, height / 3 - 10).build());
    }


    @Override
    public void render(DrawContext drawContext, int mouseX, int mouseY, float delta) {
        renderBackground(drawContext);
        super.render(drawContext, mouseX, mouseY, delta);

        final var text = "§l§nFastDoll v" + VERSION;
        drawContext.drawText(textRenderer, text, (int) (width / 2f - textRenderer.getWidth(text) / 2f),
                (int) (height / 3f - textRenderer.fontHeight / 2f - 25), 0xffffffff, false);
    }

    @Override
    public void close() {
        assert this.client != null;
        this.client.setScreen(_super);
    }
}
