package net.kekse.command.impl;

import net.kekse.command.Command;
import net.kekse.command.CommandInfo;
import net.kekse.exception.CommandException;
import net.kekse.util.ChatUtil;

@CommandInfo(
        name = "keksninolikestouchinglittlekids",
        usage = "KeksNinoLikesTouchingLittleKids",
        description = "KeksNinoLikesTouchingLittleKids",
        aliases = {}
)
public final class KeksNinoLikesTouchingLittleKidsCommand extends Command {

    @Override
    public void execute(String... args) throws CommandException {
        for (int i = 0; i < 3; i++) {
            ChatUtil.addChatMessage("&fdaddy chill");
        }
    }
}