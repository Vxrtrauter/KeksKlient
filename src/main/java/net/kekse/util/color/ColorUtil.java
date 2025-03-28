package net.kekse.util.color;

import java.awt.*;

public class ColorUtil {

    public static Color chroma(int delay, float saturation, float brightness) {
        double chroma = Math.ceil((double) (System.currentTimeMillis() + delay) / 20);
        chroma %= 360;
        return Color.getHSBColor((float) (chroma / 360), saturation, brightness);
    }

    public static Color orangeFade(int delay, float saturation, float brightness) {
        double time = (System.currentTimeMillis() + delay) / 20.0;
        float baseHue = 45.0f / 360.0f;
        float secondHue = 38.0f / 360.0f;
        float hue = (float) (baseHue + (secondHue - baseHue) * Math.sin(time * 0.05));
        return Color.getHSBColor(hue, saturation, brightness);
    }
}
