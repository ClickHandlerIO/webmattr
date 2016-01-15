package webmattr.action;

import webmattr.Try;
import webmattr.Func;

import javax.inject.Provider;
import java.util.List;

/**
 * Maintains the lifecycle of a single Action call.
 *
 * @author Clay Molocznik
 */
public class ActionCall<IN, OUT> {
    private IN request;
    private ErrorCallback errorCallback;
    private ResponseCallback<OUT> response;
    private Func.Run dispatching;
    private AlwaysCallback alwaysCallback;
    private boolean wasDispatched;
    private Func.Run1<IN> dispatch;
    private Provider<IN> requestProvider;
    private List<Ref> refs;
    private int timeoutMillis;

    ActionCall(Func.Run1<IN> dispatch, Provider<IN> requestProvider) {
        this.dispatch = dispatch;
        this.requestProvider = requestProvider;
    }

    public int timeoutMillis() {
        return timeoutMillis;
    }

    /**
     * @param timeoutMillis
     * @return
     */
    public ActionCall<IN, OUT> timeoutMillis(int timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
        return this;
    }

    /**
     * @param request
     * @return
     */
    public ActionCall<IN, OUT> in(IN request) {
        this.request = request;
        enqueueSend();
        return this;
    }

    /**
     * @param callback
     * @return
     */
    public ActionCall<IN, OUT> in(RequestCallback<IN> callback) {
        this.request = requestProvider.get();
        callback.run(this.request);
        enqueueSend();
        return this;
    }

    /**
     * @param callback
     * @return
     */
    public ActionCall<IN, OUT> out(ResponseCallback<OUT> callback) {
        this.response = callback;
        return this;
    }

    /**
     * @param callback
     * @return
     */
    public ActionCall<IN, OUT> dispatching(Func.Run callback) {
        this.dispatching = callback;
        return this;
    }

    /**
     * @param errorCallback
     * @return
     */
    public ActionCall error(ErrorCallback errorCallback) {
        this.errorCallback = errorCallback;
        return this;
    }

    /**
     * @param alwaysCallback
     * @return
     */
    public ActionCall<IN, OUT> always(AlwaysCallback alwaysCallback) {
        this.alwaysCallback = alwaysCallback;
        return this;
    }

    private void enqueueSend() {
        Try.later(this::send);
    }

    void out(OUT response) {
        if (this.response != null) {
            try {
                this.response.call(response);
            } catch (Throwable e) {
                failed(e);
            } finally {
                this.response = null;
                always();
            }
        }
    }

    void error(Throwable e) {
        if (errorCallback != null) {
            try {
                errorCallback.run(e);
            } finally {
                errorCallback = null;
                always();
            }
        }
    }

    void always() {
        if (alwaysCallback != null) {
            try {
                alwaysCallback.run(wasDispatched);
            } finally {
                alwaysCallback = null;
                cleanup();
            }
        }
    }

    void failed(Throwable e) {
        if (errorCallback != null) {
            try {
                errorCallback.run(e);
            } catch (Throwable e2) {
                // Ignore.
            } finally {
                errorCallback = null;

                // Call always.
                always();
            }
        }
    }

    public ActionCall<IN, OUT> send() {
        if (dispatch != null) {
            try {
                if (dispatching != null) {
                    try {
                        dispatching.run();
                    } finally {
                        dispatching = null;
                    }
                }

                try {
                    dispatch.run(request);
                } finally {
                    dispatch = null;
                    wasDispatched = true;
                }
            } catch (Throwable e) {
                failed(e);
            } finally {
                dispatch = null;
                dispatching = null;
            }
        }
        return this;
    }

    public void cleanup() {
        try {
            requestProvider = null;
            errorCallback = null;
            dispatch = null;
            dispatching = null;
            always();
        } finally {
            clearRefs();
        }
    }

    private void clearRefs() {
        if (refs != null) {
            for (Ref ref : refs) {
                try {
                    ref.remove();
                } catch (Throwable e) {
                    // Ignore.
                }
            }
            refs.clear();
            refs = null;
        }
    }

    interface Ref {
        void remove();
    }
}