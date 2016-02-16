package io.clickhandler.web.remoting;

import io.clickhandler.web.JSON;
import io.clickhandler.web.action.AbstractAction;
import io.clickhandler.web.action.TimedOutException;

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
