package webmattr.router;

import jsinterop.annotations.JsFunction;

/**
 *
 */
@JsFunction
public interface OnEnterFunction {
    void call(RouteProps nextState, ReplaceStateFunction replaceState);
}
