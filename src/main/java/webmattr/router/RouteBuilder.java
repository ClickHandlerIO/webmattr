package webmattr.router;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public abstract class RouteBuilder {
    @Inject
    Provider<RouteGatekeeper> routeGatekeeperProvider;

    private Route[] routes;

    private Map<String, Reg> regs = new HashMap<>();

    protected void construct(Location location) {
        build(location);
        constructTree();
    }

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

    protected <R extends RouteProxy<A>, A, P extends RouteProps<A>, S> void register(RouteComponent<R, A, P, S> component) {
        // Instantiate RouteProxy.
        final RouteProxy<A> proxy = component.getRouteProxyProvider().get();

        // Add to Reg map.
        regs.put(proxy.getClass().getName(),
            new Reg(
                new Route()
                    .path(proxy.path())
                    .onEnter((nextState, replaceState) -> {
                        if (proxy.onEnter(nextState, replaceState)) {
                            routeGatekeeperProvider.get().onEnter(proxy, nextState, replaceState);
                        }
                    })
                    .onLeave(
                        () -> {
                            final Object result = proxy.onLeave();
                            if (result != null) {
                                return result;
                            }
                            return routeGatekeeperProvider.get().onLeave(proxy);
                        }
                    )
                    .component(component.getReactClass()),
                proxy
            )
        );
    }

    protected void constructTree() {
        // Init top level Reg list.
        // These Routes will be the ones supplied.
        // All other's will be descendants of these.
        final List<Reg> topLevel = new ArrayList<>();

        for (Reg reg : regs.values()) {
            // Get the parent RouteProxy.
            RouteProxy parent = reg.proxy.parent();

            // Is current Reg a child?
            if (parent != null) {
                Reg parentReg = regs.get(parent.getClass().getName());
                if (parentReg == null) {
                    String parentPath = parent.path();
                    if (parentPath == null) {
                        parentPath = "";
                    } else if (!parentPath.startsWith("/")) {
                        parent = parent.parent();

                        // Find next registered parent.
                        while (parent != null) {
                            // Is the parent path already fully resolved to an absolute path?
                            if (!parentPath.startsWith("/")) {
                                // Prepend next parent path if necessary.
                                String nextParentPath = parent.path();
                                if (nextParentPath != null) {
                                    parentPath = nextParentPath + "/" + parentPath;
                                }
                            }
                            parentReg = regs.get(parent.getClass().getName());
                            // Did we find one?
                            if (parentReg != null) {
                                break;
                            }
                            // Walk up the tree.
                            parent = parent.parent();
                        }
                    }

                    String currentPath = reg.route.path;
                    if (currentPath == null) {
                        currentPath = "";
                    } else if (currentPath.startsWith("/")) {
                        currentPath = currentPath.substring(1);
                    }

                    // Prepend the transparent parent's path to ReactRouter Route.
                    reg.route.path(parentPath + "/" + currentPath);
                }
                // Was a parent Reg found?
                if (parentReg != null) {
                    // Add current reg as child.
                    parentReg.addChild(reg);
                } else {
                    // This reg is a top level Reg with a transparent parent RouteProxy.
                    // This is most likely for organizational structure.
                    topLevel.add(reg);
                }
            } else {
                // No parent equals top level.
                topLevel.add(reg);
            }
        }

        // Let's build ReactRouter Routes.
        for (Reg reg : regs.values()) {
            if (reg.children == null || reg.children.isEmpty()) {
                continue;
            }
            final Route[] childRoutes = new Route[reg.children.size()];
            for (int i = 0; i < reg.children.size(); i++) {
                final Reg child = reg.children.get(i);
                // Is this child the IndexRoute?
                if (child.proxy.isIndex()) {
                    // Set as IndexRoute.
                    reg.route.indexRoute = child.route;
                }
                childRoutes[i] = reg.route;
            }
            reg.route.childRoutes(childRoutes);
        }
        this.routes = new Route[topLevel.size()];
        for (int i = 0; i < topLevel.size(); i++) {
            this.routes[i] = topLevel.get(i).route;
        }
    }

    /**
     * @param location
     * @param callback
     */
    public void call(Location location, RouteCallback callback) {
        if (routes == null) {
            construct(location);
        }
        callback.run(null, routes != null && routes.length > 0 ? routes[0] : null);
    }

    /**
     * @param location
     * @param callback
     */
    public void call(Location location, RoutesCallback callback) {
        if (routes == null) {
            construct(location);
        }
        callback.run(null, routes);
    }

    protected static class Reg {
        private final Route route;
        private final RouteProxy<?> proxy;
        private List<Reg> children = null;

        public Reg(Route route, RouteProxy<?> proxy) {
            this.route = route;
            this.proxy = proxy;
        }

        private void addChild(Reg child) {
            if (children == null) {
                children = new ArrayList<>();
            }
            children.add(child);
        }
    }
}
