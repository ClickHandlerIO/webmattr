package webmattr.router;

import jsinterop.annotations.JsFunction;

/**
 *
 */
@JsFunction
public interface OnEnterRoute {
    void onEnter(RouteProps nextState, ReplaceStateFunction replaceState);
}
