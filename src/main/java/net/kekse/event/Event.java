package net.kekse.event;

import lombok.Getter;
import lombok.Setter;
import me.zero.alpine.event.CancellableEvent;
import me.zero.alpine.event.EventPhase;

import java.util.Optional;

@Getter
@Setter
public abstract class Event extends CancellableEvent {

    private EventPhase eventPhase;
    private EventFlow eventFlow;

    private boolean isPhase(EventPhase expectedPhase) {
        return Optional.ofNullable(eventPhase).map(
                p -> p == expectedPhase).orElse(false
        );
    }

    private boolean isDirection(EventFlow expectedDir) {
        return Optional.ofNullable(eventFlow).map(
                d -> d == expectedDir).orElse(false
        );
    }

    public boolean isPre() {
        return isPhase(EventPhase.PRE);}

    public boolean isPost() {
        return isPhase(EventPhase.POST);}

    public boolean isOn() {
        return isPhase(EventPhase.ON);}

    public boolean isIncoming() {
        return isDirection(EventFlow.INBOUND);}

    public boolean isOutgoing() {
        return isDirection(EventFlow.OUTBOUND);}
}
