package net.kekse.event.impl.packet;

import lombok.Getter;
import lombok.Setter;
import net.kekse.event.Event;
import net.minecraft.network.Packet;

@Getter
@Setter
public class EventPacket extends Event {
    private Packet<?> packet;
    private boolean cancelled;

    public static class Send extends EventPacket {
        public Send(Packet<?> packet) {
            super(packet);
        }
    }

    public static class Receive extends EventPacket {
        public Receive(Packet<?> packet) {
            super(packet);
        }
    }

    public EventPacket(Packet<?> packet) {
        this.packet = packet;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isCancelled() {
        return cancelled;
    }
}