package net.kekse.util.packet;

import net.minecraft.network.Packet;

import static net.kekse.command.Command.mc;

public class PacketUtil {

    public static void send(final Packet<?> packet) {
        mc.getNetHandler().addToSendQueue(packet);
    }

    public static void sendNoEvent(final Packet<?> packet) {
        mc.getNetHandler().addToSendQueueUnregistered(packet);
    }
}
