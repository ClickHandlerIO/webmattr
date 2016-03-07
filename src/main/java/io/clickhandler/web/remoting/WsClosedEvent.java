package io.clickhandler.web.remoting;

/**
 *
 */
public class WsClosedEvent {
    public final WsDispatcher dispatcher;

    public WsClosedEvent(WsDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }
}
