package net.kekse.module;


import lombok.Getter;
import lombok.Setter;
import me.zero.alpine.listener.Subscriber;
import net.kekse.KeksKlient;
import net.kekse.settings.Setting;
import net.minecraft.client.Minecraft;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public abstract class Module implements Subscriber {

    private boolean toggled;

    private final String name, description;
    private final Category category;

    private final List<Setting> sList = new ArrayList<>();

    @Setter
    private int key;

    protected final Minecraft mc = KeksKlient.INSTANCE.getMc();


    public Module() {
        ModuleInfo info = getClass().getAnnotation(ModuleInfo.class);
        Validate.notNull(info, "CONFUSED ANNOTATION EXCEPTION");


        this.name = info.name();
        this.description = info.description();
        this.category = info.category();


    }

    protected void addSetting(Setting setting) {
        sList.add(setting);
    }

    protected void addSettings(Setting... settings) {
        sList.addAll(Arrays.asList(settings));
    }


    public void onEnable() {
        KeksKlient.BUS.subscribe(this);

    }

    public  void onDisable() {
        KeksKlient.BUS.unsubscribe(this);
    }


    public void onToggle() {}

    public void toggle() {
        onToggle();
        if (toggled) {
            toggled = false;
            onDisable();

        } else {
            toggled = true;
            onEnable();
        }
    }

    public void setToggled(boolean toggled) {

        onToggle();
        if (toggled) {
            this.toggled = true;
            onEnable();
        } else {
            this.toggled = false;
            onDisable();
        }
    }
}



