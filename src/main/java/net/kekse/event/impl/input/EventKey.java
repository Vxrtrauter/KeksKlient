package net.kekse.event.impl.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kekse.event.Event;

@Getter
@AllArgsConstructor

public final class EventKey extends Event {
    private final int key;

}
