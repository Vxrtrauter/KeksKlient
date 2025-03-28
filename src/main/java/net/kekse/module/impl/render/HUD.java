package net.kekse.module.impl.render;

import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.kekse.KeksKlient;
import net.kekse.event.impl.render.Event2D;
import net.kekse.module.Category;
import net.kekse.module.Module;
import net.kekse.module.ModuleInfo;
import net.kekse.util.color.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;


@ModuleInfo(
        name = "HUD",
        description = "Renders Information on your screen",
        category = Category.Render
)


public class HUD extends Module {
    private FontRenderer fr = null;

    public HUD() {
        fr = mc.fontRendererObj;
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
    private final Listener<Event2D> on2D = new Listener<>(e -> {
        float colorOffset = 0;
        GL11.glPushMatrix();
        GL11.glScaled(1.5,1.5, 1.5);
        fr.drawString(KeksKlient.INSTANCE.getName(), 5, 5, ColorUtil.orangeFade(-100, 0.92f, 0.8f).getRGB());
        GL11.glPopMatrix();


        fr.drawString("FPS: " + Minecraft.getDebugFPS(), 7, 482, Color.white.getRGB());
        fr.drawString("BPS: " + getBPS(), 7, 495, Color.white.getRGB());




        float offset = 3;


        for (Object module : KeksKlient.INSTANCE.getMm().getModules().values().stream()
                .filter(m -> !Arrays.asList("HUD", "ClickGUI").contains(m.getName()))
                .filter(Module::isToggled)
                .sorted(Comparator.comparing(m -> fr.getStringWidth(m.toString())).reversed())
                .toArray()) {

            Module mod = (Module) module;
            String name = mod.getName();
            int width = fr.getStringWidth(name);
            float x = 953 - width;
            float y = offset + 4;

            // Draw diagonal gradient text (top-left to bottom-right)
            GL11.glPushMatrix();
            float xOffset = 0;
            for (int i = 0; i < name.length(); i++) {
                String character = name.substring(i, i + 1);
                // Change the offset calculation to create top-left to bottom-right gradient
                Color color = ColorUtil.orangeFade((int) (colorOffset + i * 50 + offset * 20), 0.92f, 0.8f);
                fr.drawString(character, x + xOffset, y, color.getRGB(), false);
                xOffset += fr.getStringWidth(character);
            }
            GL11.glPopMatrix();

            offset += 12;
            colorOffset -= 100; // Changed from -= 300 to += 100 for the new direction
        }
    });

    private String getBPS() {
        final float ticks = mc.timer.ticksPerSecond + mc.timer.timerSpeed;
        final double deltaX = mc.thePlayer.lastTickPosX - mc.thePlayer.posX;
        final double deltaZ = mc.thePlayer.lastTickPosZ - mc.thePlayer.posZ;
        final double bps = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ) * ticks;
        return String.format("%.2f", bps);
    }

}

