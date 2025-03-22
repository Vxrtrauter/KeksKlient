package net.kekse.exception;

import net.kekse.util.ChatUtil;
import net.minecraft.util.EnumChatFormatting;

public class CommandException extends IllegalArgumentException {

    public CommandException(String message) {
        super(message);
        ChatUtil.addChatMessage(EnumChatFormatting.RED + message);

    }

}
