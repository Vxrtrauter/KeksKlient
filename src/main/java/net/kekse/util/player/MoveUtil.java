package net.kekse.util.player;



import net.kekse.KeksKlient;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;


public class MoveUtil {

    protected static final Minecraft mc = KeksKlient.INSTANCE.getMc();

    public static boolean isMoving() {
        return mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0;
    }

    public static void startSprinting() {
        if (mc.thePlayer.isCollidedHorizontally) return;
        if (mc.thePlayer.moveForward <= 0) return;
        if (mc.thePlayer.isUsingItem()) return;
        if (mc.thePlayer.isSneaking()) return;

        mc.thePlayer.setSprinting(true);
    }

}
