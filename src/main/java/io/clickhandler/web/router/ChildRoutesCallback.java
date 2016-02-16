package io.clickhandler.web.router;

import jsinterop.annotations.JsFunction;

/**
 *
 */
@JsFunction
public interface ChildRoutesCallback {
    void call(Location location, RoutesCallback callback);
}
