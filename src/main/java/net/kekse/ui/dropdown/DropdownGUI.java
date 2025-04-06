package net.kekse.ui.dropdown;

import net.kekse.KeksKlient;
import net.kekse.module.Category;
import net.kekse.module.impl.render.ClickGUI;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DropdownGUI extends GuiScreen {
    private final List<Frame> frames;

    public DropdownGUI() {
        frames = new ArrayList<>();
        int offset = 30;
        for(Category cat : Category.values()) {
            frames.add(new Frame(cat, offset, 30, 90, 18));
            offset += 130;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for(Frame frames : frames) { frames.drawScreen(mouseX, mouseY, partialTicks); }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        for(Frame frame : frames) { frame.keyTyped(typedChar, keyCode); }

        if (keyCode == Keyboard.KEY_ESCAPE || keyCode == Keyboard.KEY_RSHIFT) {
            KeksKlient.INSTANCE.getMm().getModule(ClickGUI.class).toggle();
        }

        super.keyTyped(typedChar, keyCode);
    }


    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for(Frame frame : frames) { frame.mouseClicked(mouseX, mouseY, mouseButton); }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}

