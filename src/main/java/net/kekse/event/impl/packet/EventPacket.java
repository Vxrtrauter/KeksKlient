package net.kekse.event.impl.packet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.kekse.event.Event;
import net.minecraft.network.Packet;

@Getter
@Setter
@AllArgsConstructor

public final class EventPacket extends Event {
    private Packet<?> packet;
}
