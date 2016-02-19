package io.clickhandler.web.react;

import com.google.web.bindery.event.shared.HandlerRegistration;
import elemental.dom.Element;
import io.clickhandler.web.Bus;
import io.clickhandler.web.BusDelegate;
import io.clickhandler.web.Func;
import io.clickhandler.web.Try;
import io.clickhandler.web.action.*;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

import javax.inject.Provider;

/**
 * Internal React Component class.
 */
@JsType(isNative = true)
public final class ReactComponent<P, S> {
    private ReactComponent() {
    }

    @JsProperty(name = React.BUS)
    protected native BusDelegate getBus();

    @JsProperty(name = React.BUS)
    native void setBus(BusDelegate bus);

    @JsProperty(name = React.ACTION_CALLS)
    protected native ActionCalls getActionCalls();

    @JsProperty(name = React.ACTION_CALLS)
    native void setActionCalls(ActionCalls actionCalls);

    @JsOverlay
    public final void register(HandlerRegistration handlerRegistration) {
        getBus().register(handlerRegistration);
    }

    /**
     * @param eventClass
     * @param callback
     * @param <T>
     * @return
     */
    @JsOverlay
    public final <T> HandlerRegistration subscribe(Class<T> eventClass, EventCallback<T> callback) {
        return getBus().subscribe(eventClass, callback);
    }

    /**
     * @param named
     * @param callback
     * @param <T>
     * @return
     */
    @JsOverlay
    public final <T> HandlerRegistration subscribe(Bus.TypeName<T> named, EventCallback<T> callback) {
        return getBus().subscribe(named, callback);
    }

    /**
     * @param name
     * @param callback
     * @param <T>
     * @return
     */
    @JsOverlay
    public final <T> HandlerRegistration subscribe(String name, EventCallback<T> callback) {
        return getBus().subscribe(name, callback);
    }

    /**
     * @param event
     * @param <T>
     */
    @JsOverlay
    public final <T> void publish(T event) {
        getBus().publish(event);
    }

    @JsOverlay
    public final <T> void publish(Bus.TypeName<T> name, T event) {
        getBus().publish(name, event);
    }

    @JsOverlay
    public final <T> void publish(String name, T event) {
        getBus().publish(name, event);
    }

    @JsOverlay
    public final void cleanup() {
        final BusDelegate bus = getBus();
        if (bus != null) {
            bus.clear();
            setBus(null);
        }

        final ActionCalls calls = getActionCalls();
        if (calls != null) {
            Try.silent(() -> calls.clear());
            setActionCalls(null);
        }
    }

    @JsOverlay
    public final <H extends AbstractAction<IN, OUT>, IN, OUT> ActionCall<IN, OUT> dispatch(Provider<H> action) {
        ActionCalls calls = getActionCalls();
        if (calls == null) {
            calls = new ActionCalls();
            setActionCalls(calls);
        }

        final ActionCall<IN, OUT> call = ActionBuilder.action(action);
        calls.add(call);
        return call;
    }

    @JsMethod(name = React.ACTION)
    public native <H extends AbstractAction<IN, OUT>, IN, OUT> ActionCall<IN, OUT> action(
        Provider<H> action
    );

    @JsMethod(name = React.GET_REF)
    public native <T extends Element> T ref(Ref<T> ref);

    @JsMethod(name = React.GET_PROPERTY)
    public native <T> T getProperty(String name);

    @JsMethod(name = React.GET_PROPS)
    public native P getProps();

    @JsMethod(name = React.GET_PROPS)
    public native P props();

    @JsMethod(name = React.GET_STATE)
    public native S getState();

    /**
     * @param state
     */
    @JsMethod
    public native void setState(Object state);

    @JsMethod(name = React.GET_STATE)
    public native S state();

    /**
     * @param state
     */
    @JsMethod
    public native void setState(Object state, Func.Run callback);

    @JsMethod
    public native void replaceState(Object state);

    @JsMethod
    public native void replaceState(Object state, Func.Run callback);

    @JsMethod
    public native void forceUpdate();

    @JsMethod
    public native void forceUpdate(Func.Run callback);
}
