package net.kekse.module.impl.render;


import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.kekse.KeksKlient;
import net.kekse.event.impl.render.Event2D;
import net.kekse.module.Category;
import net.kekse.module.Module;
import net.kekse.module.ModuleInfo;
import org.lwjgl.input.Keyboard;

@ModuleInfo(
        name = "ClickGUI",
        description = "Opens the ClickGUI",
        category = Category.Render
)

public class ClickGUI extends Module {

    public ClickGUI() {
        setKey(Keyboard.KEY_RSHIFT);
    }


    @Override
    public void onEnable() {
        super.onEnable();
        System.out.println("ClickGUI module enabled");
    }

    @Override
    public void onDisable() {
        mc.displayGuiScreen(null);
        super.onDisable();
    }


    @Subscribe
    private final Listener<Event2D> on2D = new Listener<>(e -> {
        mc.displayGuiScreen(KeksKlient.INSTANCE.getGui());
    });




}
