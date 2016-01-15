package webmattr.router;

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import webmattr.react.Props;

/**
 *
 */
@JsType
public class PropsRoute<T> extends Props<T> {
    @JsProperty
    public Route route;
}
