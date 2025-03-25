package net.kekse.module.impl.movement;

import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.kekse.event.impl.update.EventUpdate;
import net.kekse.module.Category;
import net.kekse.module.Module;
import net.kekse.module.ModuleInfo;
import net.kekse.util.player.MoveUtil;
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
        if (mc.thePlayer == null || mc.theWorld == null) return;


        if (MoveUtil.isMoving()) {
            MoveUtil.startSprinting();
            if (mc.thePlayer.onGround) {
                mc.thePlayer.motionY *= 1.02;
                mc.thePlayer.motionX *= 1.02;
                mc.thePlayer.jump();
            }

            if (!mc.thePlayer.onGround && mc.thePlayer.motionY < 0) {
                mc.thePlayer.motionY -= 0.08;
            }

        }

    // doesnt bypass shit yet
    // if u wanna make it bypass remove line 49-51 (if (!mc.thePlayer.onGround...)
    // and also make the two motionY & motionX values to 1.00
    // its basically a legitHop if you do that


});








}
