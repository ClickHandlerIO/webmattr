package webmattr.router;

import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsType;
import webmattr.react.ComponentSpec;

import javax.inject.Provider;

/**
 *
 */
@JsType
public class Route {
    /**
     * The path used in the URL.
     * <p/>
     * It will concat with the parent route's path unless it starts with /, making it an absolute path.
     * <p/>
     * Note: Absolute paths may not be used in route config that is dynamically loaded.
     * <p/>
     * If left undefined, the router will try to match the child routes.
     */
    public String path;

    /**
     * A single component to be rendered when the route matches the URL. It can be rendered by the
     * parent route component with this.props.children.
     */
    public Object component;

    /**
     * A single component to be rendered when the route matches the URL.
     * It can be rendered by the parent route component with this.props.children.
     */
    public Object components;

    /**
     *
     */
    public Route indexRoute;

    /**
     * Asynchronously get the index route.
     */
    public ChildRouteCallback getIndexRoute;

    /**
     *
     */
    public Route[] childRoutes;

    /**
     * Async
     */
    public ChildRoutesCallback getChildRoutes;

    /**
     * Same as component but asynchronous, useful for code-splitting.
     */
    public GetComponentCallback getComponent;

    /**
     * Same as component but asynchronous, useful for code-splitting.
     */
    public GetComponentsCallback getComponents;

    /**
     *
     */
    public OnEnterFunction onEnter;

    @JsIgnore
    public Route path(String path) {
        this.path = path;
        return this;
    }

    @JsIgnore
    public Route component(Object component) {
        this.component = component;
        return this;
    }

    @JsIgnore
    public Route component(ComponentSpec component) {
        this.component = component.getReactClass();
        return this;
    }

    @JsIgnore
    public Route getComponent(GetComponentCallback getComponent) {
        this.getComponent = getComponent;
        return this;
    }

    @JsIgnore
    public Route components(Object... components) {
        this.components = components;
        return this;
    }

    @JsIgnore
    public Route getComponents(GetComponentsCallback getComponents) {
        this.getComponents = getComponents;
        return this;
    }

    @JsIgnore
    public Route indexRoute(Route indexRoute) {
        this.indexRoute = indexRoute;
        return this;
    }

    @JsIgnore
    public Route getIndexRoute(ChildRouteCallback getIndexRoute) {
        this.getIndexRoute = getIndexRoute;
        return this;
    }

    @JsIgnore
    public Route childRoutes(Route... childRoutes) {
        this.childRoutes = childRoutes;
        return this;
    }

    @JsIgnore
    public Route getChildRoutes(ChildRoutesCallback getChildRoutes) {
        this.getChildRoutes = getChildRoutes;
        return this;
    }

    @JsIgnore
    public Route onEnter(OnEnterFunction onEnter) {
        this.onEnter = onEnter;
        return this;
    }

    @JsIgnore
    public Route onEnter(Provider<OnEnterFunction> onEnterProvider) {
        this.onEnter = onEnterProvider != null ? onEnterProvider.get() : null;
        return this;
    }
}
