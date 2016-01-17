package webmattr.router;

import elemental.client.Browser;
import webmattr.Func;
import webmattr.Try;
import webmattr.dom.ReactDOM;
import webmattr.react.ReactElement;

import java.util.HashMap;

/**
 *
 */
public abstract class CoreRouter {
    public CoreRouter() {
    }

    protected Route buildRoute() {
        registerModules();

        final Route route = new Route();
        route.path("/")
            .component(root())
            .getChildRoutes(this::handleChildRoutes);
        return route;
    }

    protected History history() {
        return ReactRouter.getHashHistory();
    }

    public void start(String elementId, Func.Run beforeRender) {
        // Create Router.
        ReactElement router = ReactRouter.create(new RouterProps()
            .history(history())
            .routes(buildRoute())
        );

        Try.run(beforeRender);

        // Render.
        ReactDOM.render(router, Browser.getDocument().getElementById(elementId));
    }

    private final HashMap<String, ModuleLoader> loaderMap = new HashMap<>();

    protected abstract RouteComponent root();

    protected void handleChildRoutes(Location location, RoutesCallback callback) {
        // Get first path.
        String p = location.getPathname().toLowerCase().trim();
        if (p.startsWith("/")) {
            p = p.substring(1);
        }
        final String path = p.split("/")[0];

        // Find module loader.
        final ModuleLoader loader = loaderMap.get(path.toLowerCase());
        if (loader != null) {
            loader.load(location, callback);
        } else {
            // Could not find Module.
            onNotResolved(location, callback);
        }
    }

    protected void onNotResolved(Location location, RoutesCallback callback) {
    }

    protected abstract void registerModules();

    protected void register(String prefix, ModuleLoader loader) {
        loaderMap.put(prefix, loader);
    }
}
