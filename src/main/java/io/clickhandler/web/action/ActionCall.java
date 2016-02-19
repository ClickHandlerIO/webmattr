package io.clickhandler.web.action;

import io.clickhandler.web.Func;
import io.clickhandler.web.Try;

import javax.inject.Provider;

/**
 * Maintains the lifecycle of a single Action call.
 *
 * @author Clay Molocznik
 */
public class ActionCall<IN, OUT> {
    ActionCalls scope;
    private IN request;
    private ErrorCallback errorCallback;
    private ResponseCallback<OUT> response;
    private Func.Run willSend;
    private AlwaysCallback alwaysCallback;
    private boolean wasDispatched;
    private Func.Run1<IN> dispatch;
    private Provider<IN> requestProvider;
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
    public ActionCall<IN, OUT> request(IN request) {
        this.request = request;
        enqueueSend();
        return this;
    }

    /**
     * @param callback
     * @return
     */
    public ActionCall<IN, OUT> buildRequest(RequestCallback<IN> callback) {
        this.request = requestProvider.get();
        callback.run(this.request);
        enqueueSend();
        return this;
    }

    /**
     * @param callback
     * @return
     */
    public ActionCall<IN, OUT> onResponse(ResponseCallback<OUT> callback) {
        this.response = callback;
        return this;
    }

    /**
     * @param callback
     * @return
     */
    public ActionCall<IN, OUT> willSend(Func.Run callback) {
        this.willSend = callback;
        return this;
    }

    /**
     * @param errorCallback
     * @return
     */
    public ActionCall onError(ErrorCallback errorCallback) {
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

    void onResponse(OUT response) {
        try {
            if (this.response != null)
                this.response.call(response);
        } catch (Throwable e) {
            failed(e);
        } finally {
            this.response = null;
            always();
        }
    }

    void onError(Throwable e) {
        try {
            if (errorCallback != null)
                errorCallback.run(e);
        } finally {
            errorCallback = null;
            always();
        }
    }

    void always() {
        if (alwaysCallback == null) return;

        try {
            Try.later(() -> alwaysCallback.run(wasDispatched));
        } finally {
            alwaysCallback = null;
            cleanup();
        }
    }

    void failed(Throwable e) {
        try {
            if (errorCallback != null)
                errorCallback.run(e);
        } catch (Throwable e2) {
            // Ignore.
        } finally {
            errorCallback = null;

            // Call always.
            always();
        }
    }

    public ActionCall<IN, OUT> send() {
        if (dispatch != null) {
            try {
                if (willSend != null) {
                    try {
                        willSend.run();
                    } finally {
                        willSend = null;
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
                willSend = null;
            }
        }
        return this;
    }

    public void cleanup() {
        cleanup(false);
    }

    void cleanup(boolean forced) {
        try {
            requestProvider = null;
            errorCallback = null;
            dispatch = null;
            willSend = null;
            always();
        } finally {
            try {
                if (scope != null && !forced) {
                    scope.remove(this);
                }
            } finally {
                scope = null;
            }
        }
    }
}