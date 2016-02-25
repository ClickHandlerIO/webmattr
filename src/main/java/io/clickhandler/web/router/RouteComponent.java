package io.clickhandler.web.router;

import io.clickhandler.web.react.Component;
import io.clickhandler.web.react.React;
import io.clickhandler.web.react.ReactComponent;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 *
 */
public abstract class RouteComponent<R extends RouteProxy<A>, A, P extends RouteProps, S> extends Component<P, S> implements HasRouter {
    private Provider<R> routeProxyProvider;
    private R routeProxy;

    public RouteComponent() {
    }

    public Provider<R> getRouteProxyProvider() {
        return routeProxyProvider;
    }

    @Inject
    public void setRouteProxyProvider(Provider<R> routeProxyProvider) {
        this.routeProxyProvider = routeProxyProvider;
        this.routeProxy = routeProxyProvider.get();
    }

    @Override
    protected void addContextTypes(ContextTypes contextTypes) {
        contextTypes.set("router", React.PropTypes.object(true));
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
