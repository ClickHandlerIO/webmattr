package webmattr.router;

import webmattr.react.React;
import webmattr.react.ReactComponent;

/**
 *
 */
public interface HasRouter {
    default Router getRouter(ReactComponent $this) {
        return React.get($this.getProperty("context"), "router");
    }
}
