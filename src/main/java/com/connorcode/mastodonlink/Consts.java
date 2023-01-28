package com.connorcode.mastodonlink;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class Consts {
    public static final Style baseStyle = Style.style()
            .color(TextColor.color(NamedTextColor.GRAY))
            .decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE)
            .build();
}
