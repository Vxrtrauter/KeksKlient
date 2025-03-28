package net.kekse.module.impl.combat;

import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.kekse.event.impl.update.EventUpdate;
import net.kekse.module.Category;
import net.kekse.module.Module;
import net.kekse.module.ModuleInfo;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.input.Keyboard;

@ModuleInfo(
        name = "NoHitDelay",
        description = "Removes the 1.8.9 HitDelay",
        category = Category.Combat
)

public class NoHitDelay extends Module {
    public NoHitDelay() {
        setKey(Keyboard.KEY_NONE);
        toggle();
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
        mc.leftClickCounter = 0;
    });
}
