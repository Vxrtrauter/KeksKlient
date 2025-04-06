package net.kekse.ui.dropdown;

import lombok.AllArgsConstructor;
import net.kekse.KeksKlient;
import net.kekse.module.Module;
import net.kekse.util.font.GlyphPageFontRenderer;
import net.kekse.util.render.RenderUtil;
import net.kekse.util.render.hover.HoverUtil;
import net.minecraft.client.gui.FontRenderer;

import java.awt.*;
import java.io.IOException;

public class ModuleButtons {

    public Module mod;
    public Frame parent;
    public int offset;

    public ModuleButtons(Module mod, Frame parent, int offset) {
        this.mod = mod;
        this.parent = parent;
        this.offset = offset;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        FontRenderer fr = KeksKlient.INSTANCE.getMc().fontRendererObj;

        RenderUtil.rect(parent.x, offset + 30, parent.width, parent.height + 3, new Color(28, 28, 28, 255));
        RenderUtil.rect(parent.x, offset + 30, parent.width, 1, new Color(60, 60, 60, 255));

        if(mod.isToggled()) RenderUtil.rect(parent.x + 76, offset + 40, 5, 5, Color.GREEN);

        fr.drawString(mod.getName(), parent.x + 6, offset + 38, -1);

        //module descriptions
        if(HoverUtil.rectHovered(parent.x, offset + 30, parent.width, offset + 5, mouseX, mouseY)) {
            KeksKlient.INSTANCE.getFh().size20.drawString(mod.getDescription(), parent.x + 100, offset + 38, -1);
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (!parent.extended) return; //better logic, anything under this will abide by this rule
        if(HoverUtil.rectHovered(parent.x, offset + 30, parent.width, offset + 5, mouseX, mouseY) && mouseButton == 0) {
            mod.toggle();
        }
    }

}
