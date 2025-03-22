package net.kekse.util.render;

import net.minecraft.client.gui.Gui;

import java.awt.*;

public class RenderUtil {

    public static void rect(double x, double y, double width, double height, Color color) {
        Gui.drawRect(x, y, width, y + height, color.getRGB());
    }
}
