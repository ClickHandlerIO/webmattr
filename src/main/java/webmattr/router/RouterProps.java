package webmattr.router;

import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsType;
import webmattr.react.Props;

/**
 *
 */
@JsType
public class RouterProps extends Props {
    public Object history;
    public Route routes;

    @JsIgnore
    public RouterProps history(Object history) {
        this.history = history;
        return this;
    }

    @JsIgnore
    public RouterProps routes(Route routes) {
        this.routes = routes;
        return this;
    }
}
