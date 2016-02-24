package io.clickhandler.web.router;

import io.clickhandler.web.react.BaseProps;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 *
 */
@JsType(isNative = true)
public interface RouteProps<T> extends BaseProps {
    /**
     * The Router's history history.
     * <p/>
     * Useful mostly for transitioning around with this.props.history.pushState(state, path, query)
     *
     * @return
     */
    @JsProperty
    History getHistory();

    /**
     * @return
     */
    @JsProperty
    Location getLocation();

    /**
     * The dynamic segments of the URL.
     *
     * @return
     */
    @JsProperty
    Object getParams();

    /**
     * The route that rendered this component.
     *
     * @return
     */
    @JsProperty
    Route getRoute();
}
