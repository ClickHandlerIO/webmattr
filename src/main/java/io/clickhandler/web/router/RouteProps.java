package io.clickhandler.web.router;

import elemental.dom.Element;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import io.clickhandler.web.react.Props;

/**
 *
 */
@JsType
public class RouteProps<T> extends Props<Element> {
    /**
     * The Router's history history.
     * <p/>
     * Useful mostly for transitioning around with this.props.history.pushState(state, path, query)
     *
     * @return
     */
    @JsProperty
    public native History getHistory();

    /**
     * @return
     */
    @JsProperty
    public native Location getLocation();

    /**
     * The dynamic segments of the URL.
     *
     * @return
     */
    @JsProperty
    public native Object getParams();

    /**
     * The route that rendered this component.
     *
     * @return
     */
    @JsProperty
    public native Route getRoute();
}
