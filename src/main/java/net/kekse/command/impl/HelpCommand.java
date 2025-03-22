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
            ChatUtil.addChatMessage("\n");
            final Command command = KeksKlient.INSTANCE.getCm().getCommand(args[0])
                    .orElseThrow(() ->
                            new CommandException(String.format(EnumChatFormatting.RED + "ERROR: Command \"%s\" not found!\n", args[0])));
            return;
        }
        ChatUtil.addChatMessage("\n");
        KeksKlient.INSTANCE.getCm()
                .getCommands()
                .values()
                .stream()
                .filter(command -> !(command instanceof HelpCommand))
                .forEach(command -> ChatUtil.addChatMessage(
                        String.format(EnumChatFormatting.YELLOW + "%s "+ EnumChatFormatting.WHITE + "- " + EnumChatFormatting.GRAY + "%s", " " + command.getName(), command.getDescription() + "\n")
                ,true));

        ChatUtil.addChatMessage("\n");
    }
}
