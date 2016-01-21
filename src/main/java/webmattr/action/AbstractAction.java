package webmattr.action;

import webmattr.Bus;
import webmattr.JSON;
import webmattr.Try;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.Date;

/**
 * Actions provide a command pattern and a standard Request/Reply
 * asynchronous messaging pattern for virtually any sort of task.
 *
 * @author Clay Molocznik
 */
public abstract class AbstractAction<IN, OUT> {
    private Bus bus;
    private Provider<IN> inProvider;
    private Provider<OUT> outProvider;
    private long started;
    private int timeoutMillis = 10000;
    private ActionCall<IN, OUT> call;

    public AbstractAction() {
    }

    public int timeoutMillis() {
        return timeoutMillis;
    }

    @Inject
    void setBus(Bus bus) {
        this.bus = bus;
    }

    @Inject
    void setInProvider(Provider<IN> inProvider) {
        this.inProvider = inProvider;
    }

    @Inject
    void setOutProvider(Provider<OUT> outProvider) {
        this.outProvider = outProvider;
    }

    protected ActionCall<IN, OUT> build() {
        return call = new ActionCall<IN, OUT>(in -> {
            beforeDispatch(in);
        }, inProvider);
    }

    /**
     * @param request
     */
    protected void beforeDispatch(IN request) {
        started = new Date().getTime();
        this.timeoutMillis = call.timeoutMillis();
        bus.publish(request);
        dispatch(request);
    }

    /**
     * @param request
     */
    protected void dispatch(IN request) {
        Try.later(() -> handle(request));
    }

    /**
     * @param request
     */
    protected abstract void handle(IN request);

    /**
     * @param e
     */
    protected void error(Throwable e) {
        call.error(e);
    }

    /**
     * @param response
     */
    protected void respond(OUT response) {
        call.out(response);
    }

    /**
     * @param callback
     */
    protected void respond(ResponseCallback<OUT> callback) {
        final OUT out = outProvider.get();
        try {
            if (callback != null) {
                callback.call(out);
            }
            call.out(out);
        } catch (Throwable e) {
            call.failed(e);
        }
    }

    /**
     * @param json
     * @return
     */
    protected IN parseIn(String json) {
        final IN in = inProvider.get();
        if (in == null) {
            return null;
        }
        return JSON.parse(json, in);
    }

    /**
     * @param json
     * @return
     */
    protected OUT parseOut(String json) {
        final OUT out = outProvider.get();
        if (out == null) {
            return null;
        }
        return JSON.parse(json, out);
    }
}
