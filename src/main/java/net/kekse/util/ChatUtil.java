package net.kekse.util;

import net.kekse.KeksKlient;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public final class ChatUtil {

    public static String fix(String string) {
        return string
                .replace("&", "§")
                .replace(">>", "»")
                .replace("<<", "«");
    }

    public static void addChatMessage(final String msg) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(fix(KeksKlient.INSTANCE.getClientPrefix()) + fix(msg)));
        }

    public static void addChatMessage(final String msg, final boolean prefix) {
        if (prefix) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(fix(KeksKlient.INSTANCE.getClientPrefix()) + fix(msg)));
        } else {
            addChatMessage(msg);
        }
    }

}
