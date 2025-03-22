package net.kekse.command.impl;

import net.kekse.KeksKlient;
import net.kekse.command.Command;
import net.kekse.command.CommandInfo;
import net.kekse.exception.CommandException;
import net.kekse.module.Module;
import net.kekse.settings.Setting;
import net.kekse.settings.impl.BooleanSetting;
import net.kekse.settings.impl.DoubleSetting;
import net.kekse.settings.impl.ModeSetting;
import net.kekse.util.ChatUtil;

@CommandInfo(
        name = "setting",
        usage = "setting <command> <Setting> <Value>",
        description = "Change settings for modules",
        aliases = {"s", "set", "settings"}

)


public class SettingCommand extends Command {
    @Override
    public void execute(String... args) throws CommandException {
        Module mod = KeksKlient.INSTANCE.getMm().getModule(args[0]);



        if(args.length != 3 && args.length != 6) {
            ChatUtil.addChatMessage("&9Usage: &c\"&b" + getUsage() + "&c\"\n");
            return;
        }

        if(mod == null) {
            ChatUtil.addChatMessage("&9Module not found\n");
            return;
        }

        Setting setting = KeksKlient.INSTANCE.getSm().getSetting(mod, args[1]);

        if(setting == null) {
            ChatUtil.addChatMessage("&9Setting not found\n");
            return;
        }

        try {
            if(setting instanceof BooleanSetting) {
                BooleanSetting bs = (BooleanSetting) setting;

                if(!(args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("false"))) {
                    ChatUtil.addChatMessage("You must either give &atrue&r &4or $cfalse");
                    return;
                }


                bs.setState(args[2].equalsIgnoreCase("true"));
            }

            if(setting instanceof DoubleSetting) {
                DoubleSetting ds = (DoubleSetting) setting;
                double myDouble = Double.parseDouble(args[2]);
                if(myDouble > ds.getMaxVal()) {
                    ChatUtil.addChatMessage("&cError: Value exceeds maximum value");
                    return;
                }
                ds.setVal(myDouble);
                ChatUtil.addChatMessage("Set &b" + mod.getName() + "&r's &b" + setting.getName() + "&r to &b" + args[2]);
                return;
            }

            if(setting instanceof ModeSetting) {
                ModeSetting ms = (ModeSetting) setting;

                if(ms.getModes().stream().noneMatch(s -> s.equalsIgnoreCase(args[2]))) {
                    ChatUtil.addChatMessage("Specified module was not found");
                    return;
                }
                ms.setCurrentMode(args[2]);
                ChatUtil.addChatMessage("Set &b" + mod.getName() + "&r's &b" + setting.getName() + "&r to &b" + args[2]);
            }

        } catch (NumberFormatException e) {
            ChatUtil.addChatMessage("You must pass a number as an Argument/Invalid Number!");
            return;
        }

    }
}
