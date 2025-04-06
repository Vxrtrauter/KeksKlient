package net.kekse.settings.impl;

import lombok.Getter;
import lombok.Setter;
import net.kekse.settings.Setting;

@Setter
@Getter
public class DoubleSetting extends Setting {
    private double val, maxVal, minVal, increment, defaultVal;

    public DoubleSetting(String name, double defaultVal, double minVal, double maxVal, double increment) {
        this.name = name;
        this.defaultVal = defaultVal;
        this.minVal = minVal;
        this.maxVal = maxVal;
        this.increment = increment;
        this.val = defaultVal;
    }

    private double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }

}
