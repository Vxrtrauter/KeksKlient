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
        usage = "setting <module> <setting> <value>",
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

        if (mod == null) {
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


        if (args.length != 3) {
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
                bs.setState(Boolean.parseBoolean(args[2].toLowerCase()));
                ChatUtil.addChatMessage("&fSet &b" + mod.getName() + "&f's &b" + setting.getName() + "&f to &b" + bs.isState());
                return;
            }

            if (setting instanceof DoubleSetting) {
                DoubleSetting ds = (DoubleSetting) setting;
                double value = Double.parseDouble(args[2]);
                if (value > ds.getMaxVal()) {
                    ChatUtil.addChatMessage("&cError: &fValue exceeds maximum value");
                    return;
                }
                if (value < ds.getMinVal()) {
                    ChatUtil.addChatMessage("&cError: &fValue is below minimum value");
                    return;
                }
                ds.setVal(value);
                ChatUtil.addChatMessage("&fSet &b" + mod.getName() + "&f's &b" + setting.getName() + "&f to &b" + value);
                return;
            }

            if (setting instanceof ModeSetting) {
                ModeSetting ms = (ModeSetting) setting;
                String targetMode = ms.getModes().stream()
                        .filter(mode -> mode.equalsIgnoreCase(args[2]))
                        .findFirst()
                        .orElse(null);

                if (targetMode == null) {
                    ChatUtil.addChatMessage("&cSpecified mode was not found");
                    return;
                }
                ms.setCurrentMode(targetMode);
                ChatUtil.addChatMessage("&fSet &b" + mod.getName() + "&f's &b" + setting.getName() + "&f to &b" + targetMode);
            }
        } catch (NumberFormatException e) {
            ChatUtil.addChatMessage("&cYou must pass a valid number as an argument!");
        }
    }
}