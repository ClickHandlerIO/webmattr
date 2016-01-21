package webmattr.router;

import javax.inject.Inject;

/**
 *
 */
public class RootRoute extends SimpleRouteProxy {
    @Inject
    public RootRoute() {
        super("/");
    }
}

