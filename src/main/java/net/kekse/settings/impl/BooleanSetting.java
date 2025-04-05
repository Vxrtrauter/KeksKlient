package net.kekse.settings.impl;


import lombok.Getter;
import lombok.Setter;
import net.kekse.settings.Setting;

@Getter
@Setter
public class BooleanSetting extends Setting {



    private boolean state;


    public BooleanSetting(String name, boolean state) {
        this.name = name;
        this.state = state;
    }

    public boolean isState() {
        return state;
    }

    public boolean isEnabled() {
        return state;
    }

    private void toggle() {
        setState(!isEnabled());
    }


}
