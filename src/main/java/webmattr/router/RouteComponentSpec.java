package webmattr.router;

import webmattr.react.ComponentSpec;
import webmattr.react.React;
import webmattr.react.ReactComponent;

/**
 *
 */
public abstract class RouteComponentSpec<P extends RouteProps, S> extends ComponentSpec<P, S> implements HasRouter {
    public RouteComponentSpec() {
    }

    @Override
    protected void addContextTypes(ContextTypes contextTypes) {
        contextTypes.set("router", React.PropTypes.object(true));
    }

    @Override
    protected void componentDidMount(ReactComponent<P, S> $this) {
        super.componentDidMount($this);
        getRouter($this).setRouteLeaveHook(
            $this.getProps().getRoute(), nextLocation -> routerWillLeave($this, $this.getProps(), $this.getState(), nextLocation)
        );
    }

    protected String routerWillLeave(ReactComponent<P, S> $this, P props, S state, Location nextLocation) {
        return null;
    }
}
