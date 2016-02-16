package io.clickhandler.web.router;

/**
 *
 */
public class ExplicitRoutesBuilder extends RoutesBuilder {
    public ExplicitRoutesBuilder(Route[] routes) {
        setRoutes(routes);
    }

    @Override
    void init() {
        // Do nothing.
    }

    @Override
    protected void registerComponents() {
    }
}
