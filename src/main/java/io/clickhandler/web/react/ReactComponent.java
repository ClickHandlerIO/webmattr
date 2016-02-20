package io.clickhandler.web.react;

import com.google.web.bindery.event.shared.HandlerRegistration;
import elemental.dom.Element;
import io.clickhandler.web.Bus;
import io.clickhandler.web.BusDelegate;
import io.clickhandler.web.Func;
import io.clickhandler.web.Try;
import io.clickhandler.web.action.*;
import io.clickhandler.web.EventCallback;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

import javax.inject.Provider;

/**
 * Internal React Component class.
 */
@JsType(isNative = true)
public interface ReactComponent<P, S> {
    @JsProperty(name = React.BUS)
    BusDelegate getBus();

    @JsProperty(name = React.BUS)
    void setBus(BusDelegate bus);

    @JsProperty(name = React.ACTION_CALLS)
    ActionCalls getActionCalls();

    @JsMethod(name = React.REGISTER)
    void register(HandlerRegistration handlerRegistration);

    /**
     * @param eventClass
     * @param callback
     * @param <T>
     * @return
     */
    @JsMethod(name = React.SUBSCRIBE_1)
    <T> HandlerRegistration subscribe(Class<T> eventClass, EventCallback<T> callback);

    /**
     * @param named
     * @param callback
     * @param <T>
     * @return
     */
    @JsMethod(name = React.SUBSCRIBE_2)
    <T> HandlerRegistration subscribe(Bus.TypeName<T> named, EventCallback<T> callback);

    /**
     * @param name
     * @param callback
     * @param <T>
     * @return
     */
    @JsMethod(name = React.SUBSCRIBE_3)
    <T> HandlerRegistration subscribe(String name, EventCallback<T> callback);

    /**
     * @param event
     * @param <T>
     */
    @JsMethod(name = React.PUBLISH_1)
    <T> void publish(T event);

    @JsMethod(name = React.PUBLISH_2)
    <T> void publish(Bus.TypeName<T> name, T event);

    @JsMethod(name = React.PUBLISH_3)
    <T> void publish(String name, T event);

    @JsMethod(name = React.CLEANUP)
    void cleanup();

    @JsMethod(name = React.DISPATCH)
    <H extends AbstractAction<IN, OUT>, IN, OUT> ActionCall<IN, OUT> dispatch(Provider<H> action);

    @JsMethod(name = React.ACTION)
    <H extends AbstractAction<IN, OUT>, IN, OUT> ActionCall<IN, OUT> action(
        Provider<H> action
    );

    @JsMethod(name = React.GET_REF)
    <T extends Element> T ref(Ref<T> ref);

    @JsMethod(name = React.GET_PROPERTY)
    <T> T getProperty(String name);

    @JsMethod(name = React.GET_PROPS)
    P getProps();

    @JsMethod(name = React.GET_PROPS)
    P props();

    @JsMethod(name = React.GET_STATE)
    S getState();

    /**
     * @param state
     */
    @JsMethod
    void setState(Object state);

    @JsMethod(name = React.GET_STATE)
    S state();

    /**
     * @param state
     */
    @JsMethod
    void setState(Object state, Func.Run callback);

    @JsMethod
    void replaceState(Object state);

    @JsMethod
    void replaceState(Object state, Func.Run callback);

    @JsMethod
    void forceUpdate();

    @JsMethod
    void forceUpdate(Func.Run callback);
}
