package net.kekse.command.impl;

import net.kekse.KeksKlient;
import net.kekse.command.Command;
import net.kekse.command.CommandInfo;
import net.kekse.exception.CommandException;
import net.kekse.util.ChatUtil;
import net.minecraft.util.EnumChatFormatting;


@CommandInfo(

        name = "help",
        usage = "help <command>",
        description = "Get Help for a Command",
        aliases =  {"hlp", "hlep", "h"}


)

public final class HelpCommand extends Command {

    @Override
    public void execute(String... args) throws CommandException {
        if (args.length > 0) {
            final Command command = KeksKlient.INSTANCE.getCm().getCommand(args[0])
                    .orElseThrow(() ->
                            new CommandException("&cERROR: &fCommand &b" + args[0] + "&f not found!"));

            ChatUtil.addChatMessage("&fCommand: &b" + command.getName());
            ChatUtil.addChatMessage("&fUsage: &b" + command.getUsage());
            ChatUtil.addChatMessage("&fDescription: &b" + command.getDescription());
            return;
        }

        KeksKlient.INSTANCE.getCm()
                .getCommands()
                .values()
                .stream()
                .filter(command -> !(command instanceof HelpCommand))
                .forEach(command -> ChatUtil.addChatMessage(
                        "&b" + command.getName() + " &7- &f" + command.getDescription()
                        , true));
    }
}
