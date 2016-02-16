package io.clickhandler.web.action;

import jsinterop.annotations.JsFunction;

/**
 *
 */
@JsFunction
public interface ResponseCallback<T> {
    void call(T response);
}
