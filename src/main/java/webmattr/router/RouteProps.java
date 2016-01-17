package webmattr.router;

import elemental.dom.Element;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import webmattr.react.Props;

/**
 *
 */
@JsType
public class RouteProps<P> extends Props<Element> {
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

    /**
     * A subset of this.props.params that were directly specified in this component's route.
     * For example, if the route's path is users/:userId and the URL is /users/123/portfolios/345
     * then this.props.routeParams will be {userId: '123'}, and this.props.params
     * will be {userId: '123', portfolioId: 345}.
     *
     * @return
     */
    @JsProperty
    public native P getRouteParams();
}
