package webmattr.action;

import jsinterop.annotations.JsFunction;

/**
 *
 */
@JsFunction
public interface ResponseCallback<T> {
    void call(T response);
}
