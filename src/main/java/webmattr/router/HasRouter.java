package webmattr.router;

import webmattr.Reflection;
import webmattr.react.ReactComponent;

/**
 *
 */
public interface HasRouter {
    default Router getRouter(ReactComponent $this) {
        return Reflection.get($this.getProperty("context"), "router");
    }
}
