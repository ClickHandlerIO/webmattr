package webmattr.router;

/**
 *
 */
public class ExplicitRouteBuilder extends RouteBuilder {
    public ExplicitRouteBuilder(Route[] routes) {
        setRoutes(routes);
    }

    @Override
    void init() {
        // Do nothing.
    }

    @Override
    protected void addComponents() {
    }
}
