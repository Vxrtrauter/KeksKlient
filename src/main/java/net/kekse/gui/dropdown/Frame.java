package net.kekse.gui.dropdown;


import net.kekse.KeksKlient;
import net.kekse.module.Category;
import net.kekse.module.Module;
import net.kekse.util.render.RenderUtil;
import net.kekse.util.render.hover.HoverUtil;
import net.minecraft.client.gui.FontRenderer;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Frame {
    private final List<ModuleButtons> moduleButtons;

    public Category cat;
    public int offset;
    public boolean extended = false;
    public int x, y, width, height;

    public Frame(Category cat, int x, int y, int width, int height) {
        this.cat = cat;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        moduleButtons = new ArrayList<>();

        int offset = height;
        System.out.println(KeksKlient.INSTANCE.getMm().getModules(cat).length);
        for(Module mod : KeksKlient.INSTANCE.getMm().getModules(cat)) {
            moduleButtons.add(new ModuleButtons(mod, this, offset));
            offset += height;
        }
    }


    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        FontRenderer fr = KeksKlient.INSTANCE.getMc().fontRendererObj;

        RenderUtil.rect(x, y, width, height, new Color(35, 35, 35, 255));
        fr.drawString(cat.name(), x + 6, y + 6, -1);


        if(extended) {
            fr.drawString("-", x + 89, y + 6, -1);
            for(ModuleButtons mb : moduleButtons) {
                mb.drawScreen(mouseX, mouseY, partialTicks);
            }
        } else {
            fr.drawString("+", x + 89, y + 6, -1);
        }





    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {

    }



    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for(ModuleButtons mb : moduleButtons) {
            mb.mouseClicked(mouseX, mouseY, mouseButton);
        }
        if(HoverUtil.rectHovered(x, y, width, height, mouseX, mouseY) && mouseButton == 1) {
            extended = !extended;
        }
    }




}
