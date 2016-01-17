package webmattr.ws;

import webmattr.JSON;
import webmattr.action.AbstractAction;
import webmattr.action.TimedOutException;

import javax.inject.Inject;

/**
 *
 */
public abstract class WsAction<IN, OUT> extends AbstractAction<IN, OUT> {
    @Inject
    WsDispatcher dispatcher;

    public WsDispatcher getDispatcher() {
        return dispatcher;
    }

    /**
     *
     * @return
     */
    protected abstract String path();

    protected String stringify(IN request) {
        return JSON.stringify(request);
    }

    @Override
    protected void handle(IN request) {
        dispatcher.request(
            // Timeout Millis.
            timeoutMillis(),
            // Action name.
            path(),
            // Serialize request.
            stringify(request),
            // Handle response.
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
            // Handle timeout.
            () -> error(new TimedOutException())
        );
    }
}
