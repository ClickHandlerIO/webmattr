package webmattr.react;

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import webmattr.Func;

/**
 *
 */
@JsType(isNative = true)
public interface ReactElement<T, P extends Props<T>> {
    @JsProperty
    T getType();

    @JsProperty
    P getProps();

    @JsProperty
    Object getKey();

    @JsProperty
    Object getRef();

    void setRef(Func.Call1<Object, Object> callback);
}
