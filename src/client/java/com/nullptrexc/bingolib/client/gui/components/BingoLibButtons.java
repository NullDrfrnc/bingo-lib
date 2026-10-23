package com.nullptrexc.bingolib.client.gui.components;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.network.chat.Component;

import java.util.concurrent.Flow;

public enum BingoLibButtons {
    SINGLE(248),
    DOUBLE(122),
    TRIPPLE(80),
    QUADRUPPLE(59);

    private final int size;

    BingoLibButtons(int size) {
        this.size = size;
    }
    
    public int width() {
        return this.size;
    }

    public static Button getButton(Component component, Button.OnPress onPress, BingoLibButtons size) {
        return Button.builder(component, onPress).width(size.width()).build();
    }

    public static Button getSingleButton(Component component, Button.OnPress onPress) {
        return getButton(component, onPress, SINGLE);
    }

    public static Button getDoubleButton(Component component, Button.OnPress onPress) {
        return getButton(component, onPress, DOUBLE);
    }

    public static Button getTrippleButton(Component component, Button.OnPress onPress) {
        return getButton(component, onPress, TRIPPLE);
    }
    
    public static Button getQuadButton(Component component, Button.OnPress onPress) {
        return getButton(component, onPress, QUADRUPPLE);
    }
}
