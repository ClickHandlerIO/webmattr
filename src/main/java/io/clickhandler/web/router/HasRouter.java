package io.clickhandler.web.router;

import io.clickhandler.web.react.ReactComponent;
import io.clickhandler.web.Reflection;

/**
 *
 */
public interface HasRouter {
    default Router getRouter(ReactComponent $this) {
        return Reflection.get($this.getProperty("context"), "router");
    }
}
