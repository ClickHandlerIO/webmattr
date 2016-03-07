package io.clickhandler.web.remoting;

/**
 *
 */
public class WsConnectedEvent {
    public final WsDispatcher dispatcher;

    public WsConnectedEvent(WsDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }
}
