package net.kekse.module.impl.movement;


import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.kekse.event.impl.update.EventUpdate;
import net.kekse.module.Category;
import net.kekse.module.Module;
import net.kekse.module.ModuleInfo;
import org.lwjgl.input.Keyboard;

@ModuleInfo(
        name = "Sprint",
        description = "Sets your Sprinting to true",
        category = Category.Movement
)
public final class Sprint extends Module {

    public Sprint() {
        setKey(Keyboard.KEY_B);
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
        if (mc.thePlayer.isCollidedHorizontally) return;
        if (mc.thePlayer.moveForward <= 0) return;
        if (mc.thePlayer.isUsingItem()) return;
        if (mc.thePlayer.isSneaking()) return;

        mc.thePlayer.setSprinting(true);
    });




}
