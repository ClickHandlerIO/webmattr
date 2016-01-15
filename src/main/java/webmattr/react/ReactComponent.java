package webmattr.react;

import elemental.dom.Element;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;
import webmattr.Func;
import webmattr.action.AbstractAction;
import webmattr.action.ActionCall;

import javax.inject.Provider;

/**
 * Internal React Component class.
 */
@JsType(isNative = true)
public interface ReactComponent<P, S> {
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
