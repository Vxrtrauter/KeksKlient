package net.kekse.module.impl.render;


import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.kekse.event.impl.update.EventUpdate;
import net.kekse.module.Category;
import net.kekse.module.Module;
import net.kekse.module.ModuleInfo;
import net.kekse.settings.impl.DoubleSetting;
import net.kekse.settings.impl.ModeSetting;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.lwjgl.input.Keyboard;

@ModuleInfo(
        name = "Fullbright",
        description = "Makes ur game look bright",
        category = Category.Render
)
public final class FullBright extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "Gamma", "NightVision");

    private final DoubleSetting gamma = new DoubleSetting("Brightness", 3, 1, 5, 0.1);

    private float oldGamma;

    public FullBright() {
        addSettings(mode, gamma);
        setKey(Keyboard.KEY_G);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        oldGamma = mc.gameSettings.gammaSetting;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.thePlayer.removePotionEffect(Potion.nightVision.id);
        mc.gameSettings.gammaSetting = oldGamma;
    }

    @Subscribe
    private final Listener<EventUpdate> onUpdate = new Listener<>(e -> {
        switch(mode.getCurrentMode()) {
            case "Gamma":

                mc.thePlayer.removePotionEffect(Potion.nightVision.id);
                mc.gameSettings.gammaSetting = (float) gamma.getVal();
                break;
            case "NightVision":
                mc.gameSettings.gammaSetting = oldGamma;
                mc.thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.id, Integer.MAX_VALUE, 0));
                break;
        }

    });

}
