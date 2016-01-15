package webmattr.ws;

import webmattr.JSON;
import webmattr.action.AbstractAction;
import webmattr.action.TimedOutException;

import javax.inject.Inject;

/**
 *
 */
public abstract class WsAction<IN, OUT> extends AbstractAction<IN, OUT> {
    private WsDispatcher dispatcher;

    public WsDispatcher getDispatcher() {
        return dispatcher;
    }

    @Inject
    public void setDispatcher(WsDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    protected abstract String type();

    protected String stringify(IN request) {
        return JSON.stringify(request);
    }

    @Override
    protected void handle(IN request) {
        dispatcher.request(
            timeoutMillis(),
            type(),
            stringify(request),
            envelope -> {
                if (envelope == null) {
                    return;
                }
                if (envelope.getCode() != 200) {
                    error(new StatusCodeException(envelope.getCode()));
                } else {
                    respond(parseOut(envelope.getPayload()));
                }
            },
            () -> error(new TimedOutException())
        );
    }
}
