package net.kekse;

import lombok.Getter;
import me.zero.alpine.bus.EventBus;
import me.zero.alpine.bus.EventManager;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import me.zero.alpine.listener.Subscriber;
import net.kekse.auth.AccountManager;
import net.kekse.command.CommandManager;
import net.kekse.event.impl.input.EventKey;
import net.kekse.ui.dropdown.DropdownGUI;
import net.kekse.module.ModuleManager;
import net.kekse.settings.SettingManager;
import net.kekse.util.font.FontHelper;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;


@Getter
public enum KeksKlient implements Subscriber {

    INSTANCE;

    public static final Minecraft MC = Minecraft.getMinecraft();
    public Minecraft getMc() {
        return MC;
    }

    public static final EventBus BUS = EventManager.builder()
            .setName("root/KeksKlient")
            .setSuperListeners()
            .build();

    private String
            name = "KeksKlient",
            version = "b001 ClosedBETA",
            commandPrefix = ".",
            clientPrefix = "&8[&6Keks&8] ",
            authors = "Vxrtrauter & KeksNino";


    private ModuleManager mm;
    private CommandManager cm;
    private SettingManager sm;
    private FontHelper fh;
    private DropdownGUI gui;

    public final void init() {
        BUS.subscribe(this);
        System.out.println("Starting " + name + " " + version);
        Display.setTitle(name + " | "+ version);

        fh = new FontHelper();
        fh.init();
        mm = new ModuleManager();
        cm = new CommandManager();
        sm = new SettingManager();
        gui = new DropdownGUI();

        AccountManager.load();
    }

    public final void shutdown() {
        AccountManager.save();
        BUS.unsubscribe(this);
    }

    @Subscribe
    private final Listener<EventKey> listener = new Listener<>(e -> {
        if (this.mm != null) {
            mm.getModules().values().forEach(m -> {
                if (m.getKey() == e.getKey()) { m.toggle(); }});
        }
    });
}