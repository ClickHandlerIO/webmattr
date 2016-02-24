package io.clickhandler.web.react;

import elemental.dom.Element;
import io.clickhandler.web.BusDelegate;
import io.clickhandler.web.Func;
import io.clickhandler.web.action.AbstractAction;
import io.clickhandler.web.action.ActionCall;
import jsinterop.annotations.JsMethod;
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

//    @JsMethod(name = React.REGISTER)
//    HandlerRegistration register(HandlerRegistration handlerRegistration);
//
//    /**
//     * @param eventClass
//     * @param callback
//     * @param <T>
//     * @return
//     */
//    @JsMethod(name = React.SUBSCRIBE_1)
//    <T> HandlerRegistration subscribe(Class<T> eventClass, EventCallback<T> callback);
//
//    /**
//     * @param named
//     * @param callback
//     * @param <T>
//     * @return
//     */
//    @JsMethod(name = React.SUBSCRIBE_2)
//    <T> HandlerRegistration subscribe(Bus.TypeName<T> named, EventCallback<T> callback);
//
//    /**
//     * @param name
//     * @param callback
//     * @param <T>
//     * @return
//     */
//    @JsMethod(name = React.SUBSCRIBE_3)
//    <T> HandlerRegistration subscribe(String name, EventCallback<T> callback);
//
//    /**
//     * @param event
//     * @param <T>
//     */
//    @JsMethod(name = React.PUBLISH_1)
//    <T> void publish(T event);
//
//    @JsMethod(name = React.PUBLISH_2)
//    <T> void publish(Bus.TypeName<T> name, T event);
//
//    @JsMethod(name = React.PUBLISH_3)
//    <T> void publish(String name, T event);

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

    //    @JsMethod(name = React.GET_PROPS)
//    @JsMethod(name = React.GET_PROPS)
    @JsProperty
    P getProps();

//    @JsMethod(name = React.GET_PROPS)
//    @JsProperty
//    P props();

    //    @JsMethod(name = React.GET_STATE)
    @JsProperty
    S getState();

    @JsMethod(name = React.SET_STATE)
    void setState(Func.Run1<S> stateCallback);

    /**
     * @param state
     */
    @JsMethod
    void setState(S state);

    /**
     * @param state
     */
    @JsMethod
    void setState(S state, Func.Run callback);

    @JsMethod
    void replaceState(S state);

    @JsMethod
    void replaceState(S state, Func.Run callback);

    @JsMethod
    void forceUpdate();

    @JsMethod
    void forceUpdate(Func.Run callback);
}
