package io.clickhandler.web.action;

import jsinterop.annotations.JsFunction;

/**
 *
 */
@JsFunction
public interface RequestCallback<T> {
    void run(T request);
}
