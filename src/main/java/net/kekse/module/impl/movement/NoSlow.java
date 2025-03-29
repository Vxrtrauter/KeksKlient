package net.kekse.module.impl.movement;

import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.kekse.event.impl.player.SlowDownEvent;
import net.kekse.event.impl.update.EventUpdate;
import net.kekse.module.Category;
import net.kekse.module.Module;
import net.kekse.module.ModuleInfo;
import net.kekse.settings.impl.BooleanSetting;
import net.kekse.settings.impl.ModeSetting;
import net.kekse.util.packet.PacketUtil;
import net.minecraft.item.*;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

@ModuleInfo(
        name = "NoSlow",
        description = "Prevents slowdown when using items.",
        category = Category.Movement
)
public class NoSlow extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "BlocksMCBeta", "Verus");
    private final BooleanSetting food = new BooleanSetting("Food", true);
    private final BooleanSetting sword = new BooleanSetting("Sword", true);
    private final BooleanSetting potion = new BooleanSetting("Potion", true);
    private final BooleanSetting bow = new BooleanSetting("Bow", true);

    private int useTicks;
    private boolean isUsing;
    private int delayTicks;
    private boolean jumpTriggered;

    public NoSlow() {
        addSettings(mode, food, sword, potion, bow);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        resetState();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        resetState();
    }

    private void resetState() {
        useTicks = 0;
        isUsing = false;
        delayTicks = 0;
        jumpTriggered = false;
    }

    @Subscribe
    private final Listener<SlowDownEvent> onSlowDown = new Listener<>(event -> {
        event.setCancelled(true);
    });

    @Subscribe
    private final Listener<EventUpdate> onUpdate = new Listener<>(e -> {
        if (mc.thePlayer == null || mc.thePlayer.getHeldItem() == null) {
            resetState();
            return;
        }

        switch(mode.getCurrentMode()) {
            case "Verus":
                handleVerus();
                break;

            case "BlocksMCBeta":
                handleBlocksMCBeta();
                break;
        }
    });

    private void handleVerus() {
        ItemStack held = mc.thePlayer.getHeldItem();
        if (mc.thePlayer.isUsingItem() && isValidItem(held.getItem())) {
            if (mc.thePlayer.ticksExisted % 3 == 0) {
                PacketUtil.send(new C08PacketPlayerBlockPlacement(
                        new BlockPos(-1, -1, -1),
                        255,
                        mc.thePlayer.getHeldItem(),
                        0, 0, 0
                ));
            }
        }
    }

    private void handleBlocksMCBeta() {
        ItemStack held = mc.thePlayer.getHeldItem();
        boolean shouldUse = mc.thePlayer.isUsingItem() && isValidItem(held.getItem());

        if (shouldUse && !isUsing) {
            if (!jumpTriggered && mc.thePlayer.onGround) {
                mc.thePlayer.jump();
                jumpTriggered = true;
                delayTicks = 4;
            }
            isUsing = true;
            useTicks = 0;
        }

        if (jumpTriggered && delayTicks > 0) {
            delayTicks--;
            if (delayTicks <= 0) {
                jumpTriggered = false;
            }
            return;
        }

        if (isUsing) {
            useTicks++;
            if (shouldUse) {
                performBypass(held.getItem());
            } else {
                resetState();
            }
        }
    }

    private void performBypass(Item item) {
        int maxUseTime = (item instanceof ItemFood || item instanceof ItemPotion) ? 32 : 20;
        if (useTicks >= maxUseTime - 3) {
            PacketUtil.sendNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));

            if (useTicks % 2 == 0) {
                int slot = mc.thePlayer.inventory.currentItem;
                PacketUtil.sendNoEvent(new C09PacketHeldItemChange((slot + 1) % 9));
                PacketUtil.sendNoEvent(new C09PacketHeldItemChange(slot));

                if (mc.thePlayer.isSprinting() && !mc.thePlayer.serverSprintState) {
                    PacketUtil.send(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                    mc.thePlayer.serverSprintState = true;
                }
            }

            if (useTicks >= maxUseTime - 2) {
                PacketUtil.sendNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                PacketUtil.send(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.UP));
                resetState();
            }

            resetState();
        }
    }

    private boolean isValidItem(Item item) {
        return (food.isEnabled() && item instanceof ItemFood) ||
                (potion.isEnabled() && item instanceof ItemPotion) ||
                (sword.isEnabled() && item instanceof ItemSword) ||
                (bow.isEnabled() && item instanceof ItemBow);
    }
}