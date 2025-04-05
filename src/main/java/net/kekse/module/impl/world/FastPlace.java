package net.kekse.module.impl.world;

import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.kekse.event.impl.update.EventUpdate;
import net.kekse.module.Category;
import net.kekse.module.Module;
import net.kekse.module.ModuleInfo;
import net.kekse.settings.impl.BooleanSetting;
import net.kekse.settings.impl.DoubleSetting;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemSnowball;
import org.lwjgl.input.Keyboard;

@ModuleInfo(
        name = "FastPlace",
        description = "Places blocks faster",
        category = Category.World
)
public class FastPlace extends Module {
    private final DoubleSetting delay = new DoubleSetting("Delay", 1, 0, 4, 0.5);
    private final BooleanSetting blocksOnly = new BooleanSetting("Blocks only", true);
    private final BooleanSetting projSeparate = new BooleanSetting("Separate Projectile Delay", true);
    private final BooleanSetting pitchCheck = new BooleanSetting("Pitch Check", false);
    private final DoubleSetting projDelay = new DoubleSetting("Projectile Delay", 2, 0, 4, 0.5);

    public FastPlace() {
        addSettings(delay, blocksOnly, projSeparate, pitchCheck, projDelay);
        setKey(Keyboard.KEY_NONE);
    }

    @Subscribe
    private final Listener<EventUpdate> onUpdate = new Listener<>(e -> {
        if (mc.thePlayer == null || mc.theWorld == null) return;

        if (!pitchCheck.isEnabled() || !(mc.thePlayer.rotationPitch < 70.0F)) {
            if (blocksOnly.isEnabled()) {
                if (mc.thePlayer.getHeldItem() != null) {
                    if (mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock) {
                        setRightDelay((int) delay.getVal());
                    } else if ((mc.thePlayer.getHeldItem().getItem() instanceof ItemSnowball
                            || mc.thePlayer.getHeldItem().getItem() instanceof ItemEgg)
                            && projSeparate.isEnabled()) {
                        setRightDelay((int) projDelay.getVal());
                    }
                }
            } else {
                setRightDelay((int) delay.getVal());
            }
        }
    });

    private void setRightDelay(int delay) {
        if (delay == 0) {
            mc.rightClickDelayTimer = 0;
        } else if (delay != 4 && mc.rightClickDelayTimer == 4) {
            mc.rightClickDelayTimer = delay;
        }
    }
}