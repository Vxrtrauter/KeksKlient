package net.kekse.settings;

import net.kekse.module.Module;
import net.kekse.settings.impl.BooleanSetting;
import net.kekse.settings.impl.DoubleSetting;
import net.kekse.settings.impl.ModeSetting;

import java.util.ArrayList;
import java.util.List;

public class SettingManager {
    private final List<Setting> settingsList = new ArrayList<>();

    public BooleanSetting getBooleanSetting(Module mod, String settingName) {
        for (Setting setting : mod.getSList()) {
            if (setting instanceof BooleanSetting) {
                BooleanSetting bs = (BooleanSetting) setting;
                if (bs.getName().equalsIgnoreCase(settingName)) {
                    return bs;
                }
            }
        }
        return null;
    }

    public ModeSetting getModeSetting(Module mod, String settingName) {
        for (Setting setting : mod.getSList()) {
            if (setting instanceof ModeSetting) {
                ModeSetting ms = (ModeSetting) setting;
                if (ms.getName().equalsIgnoreCase(settingName)) {
                    return ms;
                }
            }
        }
        return null;
    }

    public DoubleSetting getDoubleSetting(Module mod, String settingName) {
        for (Setting setting : mod.getSList()) {
            if (setting instanceof DoubleSetting) {
                DoubleSetting ds = (DoubleSetting) setting;
                if (ds.getName().equalsIgnoreCase(settingName)) {
                    return ds;
                }
            }
        }
        return null;
    }

    public Setting getSetting(Module mod, String settingName) {
        for (Setting setting : mod.getSList()) {
            if (setting.getName().equalsIgnoreCase(settingName)) {
                return setting;
            }
        }
        return null;
    }

    public List<Setting> getSettingsForModule(Module mod) {
        return mod.getSList();
    }

    public void registerSetting(Setting setting) {
        this.settingsList.add(setting);
    }

    public void unregisterSetting(Setting setting) {
        this.settingsList.remove(setting);
    }

    public void clearSettings() {
        this.settingsList.clear();
    }
}