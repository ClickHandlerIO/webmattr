package io.clickhandler.web.react;

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import io.clickhandler.web.Func;

/**
 *
 */
@JsType(isNative = true)
public interface ReactElement<T, P extends BaseProps> {
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
