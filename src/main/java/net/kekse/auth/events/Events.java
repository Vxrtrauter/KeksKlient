package net.kekse.auth.events;

import net.kekse.auth.Account;
import net.kekse.auth.AccountManager;
import net.kekse.auth.SessionManager;
import net.kekse.ui.altmanager.GuiAccountManager;
import net.kekse.util.altmanager.TextFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.IChatComponent;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

public class Events {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public void onRenderTick() {
        if (mc.currentScreen == null) {
            return;
        }

        if (mc.currentScreen instanceof GuiSelectWorld || mc.currentScreen instanceof GuiMultiplayer) {
            String displayText = TextFormatting.translate(String.format(
                    "&7Username: &3%s&r", SessionManager.get().getUsername()
            ));
            GlStateManager.disableLighting();
            mc.currentScreen.drawString(mc.fontRendererObj, displayText, 3, 3, -1);
            GlStateManager.enableLighting();
        }
    }

    public void onInitGui(GuiButton button, int width) {
        if (mc.currentScreen instanceof GuiSelectWorld || mc.currentScreen instanceof GuiMultiplayer) {
            button = new GuiButton(69, width - 106, 6, 100, 20, "Accounts");
        }

        if (mc.currentScreen instanceof GuiDisconnected) {
            try {
                Field f = GuiDisconnected.class.getDeclaredField("message");
                f.setAccessible(true);
                IChatComponent message = (IChatComponent) f.get(mc.currentScreen);
                String banText = message.getUnformattedText().split("\n\n")[0];

                handleBanMessage(banText);
            } catch (Exception e) {
                // Silent exception handling
            }
        }
    }

    public void onClick(GuiButton button) {
        if ((mc.currentScreen instanceof GuiSelectWorld || mc.currentScreen instanceof GuiMultiplayer)
                && button.id == 69) {
            mc.displayGuiScreen(new GuiAccountManager(mc.currentScreen));
        }
    }

    public void onWorldLoad() {
        ServerData serverData = mc.getCurrentServerData();
        if (serverData != null) {
            String serverIP = serverData.serverIP;
            if (serverIP.endsWith("hypixel.net") || serverIP.endsWith("hypixel.io")) {
                AccountManager.load();
                for (Account account : AccountManager.accounts) {
                    if (mc.getSession().getUsername().equals(account.getUsername())) {
                        account.setUnban(0L);
                    }
                }
                AccountManager.save();
            }
        }
    }

    private void handleBanMessage(String text) {
        if (text.equals("§r§cYou are permanently banned from this server!")) {
            AccountManager.load();
            for (Account account : AccountManager.accounts) {
                if (mc.getSession().getUsername().equals(account.getUsername())) {
                    account.setUnban(-1L);
                }
            }
            AccountManager.save();
            return;
        }

        if (text.matches("§r§cYou are temporarily banned for §r§f.*§r§c from this server!")) {
            String unban = StringUtils.substringBetween(text, "§r§f", "§r§c");
            if (unban != null) {
                handleTemporaryBan(unban);
            }
        }
    }

    private void handleTemporaryBan(String unban) {
        long time = System.currentTimeMillis();
        for (String duration : unban.split(" ")) {
            String type = duration.substring(duration.length() - 1);
            long value = Long.parseLong(duration.substring(0, duration.length() - 1));
            switch (type) {
                case "d": time += value * 86400000L; break;
                case "h": time += value * 3600000L; break;
                case "m": time += value * 60000L; break;
                case "s": time += value * 1000L; break;
            }
        }

        AccountManager.load();
        for (Account account : AccountManager.accounts) {
            if (mc.getSession().getUsername().equals(account.getUsername())) {
                account.setUnban(time);
            }
        }
        AccountManager.save();
    }
}