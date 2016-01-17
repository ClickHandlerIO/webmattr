package webmattr.router;

import webmattr.react.Component;
import webmattr.router.*;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public abstract class RouteBuilder {
    @Inject
    Provider<OnEnterRoute> onEnterProvider;

    private Route[] routes;
    private List<Route> routesList = new ArrayList<>();

    /**
     * @param location
     * @return
     */
    protected abstract void build(Location location);

    /**
     * @return
     */
    public Route[] getRoutes() {
        return routes;
    }

    /**
     * @return
     */
    protected Route register() {
        return new Route();
    }

    protected <R extends RouteProxy<A>, A, P extends RouteProps<A>, S> Route register(RouteComponent<R, A, P, S> component) {
        final Route route = new Route()
            .path(component.getRouteProxyProvider().get().getPath())
            .onEnter(onEnterProvider)
            .component(component.getReactClass());

        routesList.add(route);
        return route;
    }

    /**
     * @param path
     * @param component
     * @param <P>
     * @param <S>
     * @return
     */
    protected <P, S> Route register(String path, Component<P, S> component) {
        final Route route = new Route()
            .path(path)
            .onEnter(onEnterProvider)
            .component(component.getReactClass());

        routesList.add(route);
        return route;
    }

    /**
     * @param component
     * @param <C>
     * @param <P>
     * @param <S>
     * @return
     */
    protected <C extends Component<P, S>, P, S> Route register(C component) {
        final Route route = new Route()
            .path(buildPath(component.getClass().getName()))
            .component(component.getReactClass());

        routesList.add(route);
        return route;
    }

    /**
     * @param fqn
     * @return
     */
    protected String buildPath(String fqn) {
        final String[] parts = fqn.split(".");
        int i = 0;
        for (; i < parts.length; i++) {
            final String part = parts[i];
            if (part.equals("component")) {
                i++;
                break;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (; i < parts.length; i++) {
            sb.append(parts[i]).append("/");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    /**
     * @param location
     * @param callback
     */
    public void call(Location location, RouteCallback callback) {
        if (routes == null) {
            build(location);
            routes = routesList.toArray(new Route[routesList.size()]);
        }
        callback.run(null, routes != null && routes.length > 0 ? routes[0] : null);
    }

    /**
     * @param location
     * @param callback
     */
    public void call(Location location, RoutesCallback callback) {
        if (routes == null) {
            build(location);
            routes = routesList.toArray(new Route[routesList.size()]);
        }
        callback.run(null, routes);
    }
}
