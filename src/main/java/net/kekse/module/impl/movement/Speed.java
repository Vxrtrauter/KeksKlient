package net.kekse.module.impl.movement;

import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.kekse.event.impl.update.EventUpdate;
import net.kekse.module.Category;
import net.kekse.module.Module;
import net.kekse.module.ModuleInfo;
import net.kekse.settings.impl.ModeSetting;
import net.kekse.util.player.MoveUtil;
import org.lwjgl.input.Keyboard;

@ModuleInfo(
        name = "Speed",
        description = "Gives your Player more Speed.",
        category = Category.Movement
)


public class Speed extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "LegitHop", "Test");

    public Speed() {
        addSetting(mode);
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

        switch(mode.getCurrentMode()) {
            case "LegitHop":
                if (MoveUtil.isMoving()) {
                    if (MoveUtil.canSprint()) mc.thePlayer.setSprinting(true);
                    if (mc.thePlayer.onGround) {
                        mc.thePlayer.jump();
                    }
                }
                break;

            case "Test":
                if (MoveUtil.isMoving()) {
                    if (MoveUtil.canSprint()) mc.thePlayer.setSprinting(true);
                    if (mc.thePlayer.onGround) {
                        mc.thePlayer.jump();
                    }

                    if (!mc.thePlayer.onGround && mc.thePlayer.motionY < 0) {
                        mc.thePlayer.motionY -= 0.08;
                    }
                }
                break;

        }
        mc.gameSettings.keyBindJump.pressed = false;


    });

}
