package webmattr.router;

import jsinterop.annotations.JsFunction;

/**
 *
 */
@JsFunction
public interface RouteCallback {
    void run(Object error, Route routes);
}
