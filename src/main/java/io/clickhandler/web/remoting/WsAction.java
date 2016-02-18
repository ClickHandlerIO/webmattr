package io.clickhandler.web.remoting;

import io.clickhandler.web.Bus;
import io.clickhandler.web.JSON;
import io.clickhandler.web.action.AbstractAction;
import io.clickhandler.web.action.TimedOutException;

import javax.inject.Inject;

/**
 *
 */
public abstract class WsAction<IN, OUT> extends AbstractAction<IN, OUT> {
    @Inject
    Bus bus;
    @Inject
    WsDispatcher dispatcher;

    public Bus getBus() {
        return bus;
    }

    public WsDispatcher getDispatcher() {
        return dispatcher;
    }

    public Bus.TypeName<IN> inTypeName() {
        return null;
    }

    public Bus.TypeName<OUT> outTypeName() {
        return null;
    }

    /**
     * @return
     */
    protected abstract String path();

    /**
     * @param in
     * @param out
     * @return
     */
    protected InOut<IN, OUT> inOut(IN in, OUT out) {
        return new InOut<>(in, out);
    }

    /**
     * @param request
     * @return
     */
    protected String stringify(IN request) {
        return JSON.stringify(request);
    }

    @Override
    protected void handle(IN request) {
        final Bus.TypeName<IN> inTypeName = inTypeName();
        if (inTypeName != null) {
            bus.publish(inTypeName(), request);
        }
        dispatcher.request(
            // In TypeName for Bus.
            inTypeName(),
            // Out TypeName for Bus.
            outTypeName(),
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
                    OUT out = parseOut(envelope.getBody());
                    try {
                        respond(out);
                    } finally {
                        try {
                            final Bus.TypeName<OUT> outTypeName = outTypeName();
                            if (outTypeName != null)
                                bus.publish(outTypeName, out);
                        } finally {
                            final InOut<IN, OUT> inOut = inOut(request, out);
                            if (inOut != null)
                                bus.publish(inOut);
                        }
                    }
                }
            },
            // Handle timeout.
            () -> error(new TimedOutException())
        );
    }
}
