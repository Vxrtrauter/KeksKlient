package net.kekse.module.impl.movement;

import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.kekse.event.impl.update.EventUpdate;
import net.kekse.module.Category;
import net.kekse.module.Module;
import net.kekse.module.ModuleInfo;
import org.lwjgl.input.Keyboard;

@ModuleInfo(
        name = "Speed",
        description = "Gives your Player more Speed.",
        category = Category.MOVEMENT
)


public class Speed extends Module {

    public Speed() {
        setKey(Keyboard.KEY_X);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }


    @Subscribe
    private final Listener<EventUpdate> onUpdate = new Listener<>(e -> {




    });








}
