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
        if (args.length < 1) {
            ChatUtil.addChatMessage("&cUsage: &f" + getUsage());
            return;
        }

        Module mod = KeksKlient.INSTANCE.getMm().getModule(args[0]);

        if (args.length == 1 && mod == null) {
            ChatUtil.addChatMessage("&cModule not found");
            return;
        }

        if (args.length == 1) {
            if (mod.getSList().isEmpty()) {
                ChatUtil.addChatMessage("&fModule &b" + mod.getName() + "&f has no settings");
                return;
            }
            ChatUtil.addChatMessage("&fAvailable settings for &b" + mod.getName() + "&f:");
            for (Setting setting : KeksKlient.INSTANCE.getSm().getSettingsForModule(mod)) {
                String type = "";
                String options = "";

                if (setting instanceof BooleanSetting) {
                    type = "boolean";
                    options = "true/false";
                } else if (setting instanceof DoubleSetting) {
                    DoubleSetting ds = (DoubleSetting) setting;
                    type = "number";
                    options = ds.getMinVal() + " to " + ds.getMaxVal();
                } else if (setting instanceof ModeSetting) {
                    ModeSetting ms = (ModeSetting) setting;
                    type = "mode";
                    options = String.join(", ", ms.getModes());
                }

                ChatUtil.addChatMessage("&b" + setting.getName() + " &7(" + type + ") &bOptions: &f" + options);
            }
            return;
        }



        if (args.length != 3 && args.length != 6) {
            ChatUtil.addChatMessage("&cUsage: &f" + getUsage());
            return;
        }

        Setting setting = KeksKlient.INSTANCE.getSm().getSetting(mod, args[1]);

        if (setting == null) {
            ChatUtil.addChatMessage("&cSetting not found");
            return;
        }

        try {
            if (setting instanceof BooleanSetting) {
                BooleanSetting bs = (BooleanSetting) setting;

                if (!(args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("false"))) {
                    ChatUtil.addChatMessage("&fYou must either give &atrue&r &for &cfalse &fvalue");
                    return;
                }

                bs.setState(args[2].equalsIgnoreCase("true"));
            }

            if (setting instanceof DoubleSetting) {
                DoubleSetting ds = (DoubleSetting) setting;
                double myDouble = Double.parseDouble(args[2]);
                if (myDouble > ds.getMaxVal()) {
                    ChatUtil.addChatMessage("&cError: &fValue exceeds maximum value");
                    return;
                }
                ds.setVal(myDouble);
                ChatUtil.addChatMessage("Set &b" + mod.getName() + "&f's &b" + setting.getName() + "&f to &b" + args[2]);
                return;
            }

            if (setting instanceof ModeSetting) {
                ModeSetting ms = (ModeSetting) setting;

                if (ms.getModes().stream().noneMatch(s -> s.equalsIgnoreCase(args[2]))) {
                    ChatUtil.addChatMessage("&cSpecified mode was not found");
                    return;
                }
                ms.setCurrentMode(args[2]);
                ChatUtil.addChatMessage("Set &b" + mod.getName() + "&f's &b" + setting.getName() + "&f to &b" + args[2]);
            }

        } catch (NumberFormatException e) {
            ChatUtil.addChatMessage("&cYou must pass a number as an Argument/Invalid Number!");
        }
    }
}
