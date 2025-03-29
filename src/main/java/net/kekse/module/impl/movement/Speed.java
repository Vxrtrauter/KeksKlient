package net.kekse.module.impl.movement;

import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.kekse.event.impl.update.EventUpdate;
import net.kekse.module.Category;
import net.kekse.module.Module;
import net.kekse.module.ModuleInfo;
import net.kekse.settings.impl.ModeSetting;
import net.kekse.util.ChatUtil;
import net.kekse.util.packet.PacketUtil;
import net.kekse.util.player.MoveUtil;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;

@ModuleInfo(
        name = "Speed",
        description = "Gives your Player more Speed.",
        category = Category.Movement
)


public class Speed extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "LegitHop", "Dev");

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

    private int ticks = 0;

    @Subscribe
    private final Listener<EventUpdate> onUpdate = new Listener<>(e -> {
        if (mc.thePlayer == null || mc.theWorld == null) return;
        ticks++;

        switch(mode.getCurrentMode()) {
            case "LegitHop":
                if (MoveUtil.isMoving()) {
                    if (mc.gameSettings.keyBindForward.isKeyDown() && MoveUtil.canSprint()) {
                        mc.thePlayer.setSprinting(true);
                    }
                    if (mc.thePlayer.onGround) {
                        mc.thePlayer.jump();
                    }
                }
                break;

            case "Dev":
                if (MoveUtil.isMoving()) {
                    if (MoveUtil.canSprint() && mc.gameSettings.keyBindForward.isKeyDown()) mc.thePlayer.setSprinting(true);
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
