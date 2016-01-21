package webmattr.router;

import jsinterop.annotations.JsProperty;
import webmattr.Reflection;
import webmattr.react.Component;
import webmattr.react.React;
import webmattr.react.ReactComponent;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 *
 */
public abstract class RouteComponent<R extends RouteProxy<A>, A, P extends RouteProps<A>, S> extends Component<P, S> implements HasRouter {
    @JsProperty
    private final boolean __webmattr_route_component__$$__ = true;
    @Inject
    Provider<A> argsProvider;
    @Inject
    Provider<R> routeProxyProvider;
    public RouteComponent() {
    }

    public static native boolean is(Object obj) /*-{
        return obj && obj['__webmattr_route_component__$$__'];
    }-*/;

    public Provider<A> getArgsProvider() {
        return argsProvider;
    }

    public Provider<R> getRouteProxyProvider() {
        return routeProxyProvider;
    }

    @Override
    protected void addContextTypes(ContextTypes contextTypes) {
        contextTypes.set("router", React.PropTypes.object(true));
    }

    @Override
    protected void componentWillMount(ReactComponent<P, S> $this, P props, S state) {
        updateProps(props);
        super.componentWillMount($this, props, state);
    }

    @Override
    protected void componentWillReceiveProps(ReactComponent<P, S> $this, P nextProps) {
        updateProps(nextProps);
        super.componentWillReceiveProps($this, nextProps);
    }

    private void updateProps(P props) {
        // Create new args.
        final A args = argsProvider.get();
        // Copy from routeParams into args.
        Reflection.assign(args, Reflection.get(props, "routeParams"));
        // Replace routeParams with args.
        Reflection.set(props, "routeParams", args);
    }

    @Override
    protected void componentDidMount(ReactComponent<P, S> $this) {
        super.componentDidMount($this);

        // Set Route Leave Hook.
        getRouter($this).setRouteLeaveHook(
            $this.getProps().getRoute(),
            nextLocation -> routerWillLeave($this, $this.getProps(), $this.getState(), nextLocation)
        );
    }

    protected String routerWillLeave(ReactComponent<P, S> $this, P props, S state, Location nextLocation) {
        return null;
    }
}
