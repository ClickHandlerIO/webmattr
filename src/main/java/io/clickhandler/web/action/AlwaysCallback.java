package io.clickhandler.web.action;

import jsinterop.annotations.JsFunction;

/**
 *
 */
@JsFunction
public interface AlwaysCallback {
    void run(boolean wasDispatched);
}
