package webmattr.router;

import webmattr.Func;
import webmattr.react.React;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * Proxy to an actual Route / Component(s).
 */
public class RouteProxy<T> {
    @Inject
    History history;
    @Inject
    Provider<T> argsProvider;
    private String path;
    private boolean index;
    private RouteProxy parent;

    public RouteProxy() {
    }

    public RouteProxy(String path) {
        this.path = path;
    }

    public RouteProxy(RouteProxy parent) {
        this.parent = parent;
    }

    public RouteProxy(String path, RouteProxy parent) {
        this.path = path;
        this.parent = parent;
    }

    public RouteProxy(String path, boolean index) {
        this.path = path;
        this.index = index;
    }

    public RouteProxy(String path, RouteProxy parent, boolean index) {
        this.path = path;
        this.parent = parent;
        this.index = index;
    }

    /**
     * @return
     */
    protected String path() {
        return path;
    }

    /**
     * @return
     */
    protected RouteProxy parent() {
        return parent;
    }

    /**
     * @return
     */
    public boolean isIndex() {
        return index;
    }

    /**
     * @param nextState
     * @param replaceState
     */
    protected boolean onEnter(RouteProps nextState, ReplaceStateFunction replaceState) {
        final T args = argsProvider.get();
        React.assign(args, nextState.getParams());
        return onEnter(args, nextState, replaceState);
    }

    /**
     * @param nextState
     * @param replaceState
     */
    protected boolean onEnter(T args, RouteProps nextState, ReplaceStateFunction replaceState) {
        return true;
    }

    protected Object onLeave() {
        return null;
    }

    public Provider<T> getArgsProvider() {
        return argsProvider;
    }

    public void go() {
        go(null);
    }

    public void go(Func.Run1<T> propsCallback) {
        final T props = getArgsProvider().get();
        if (propsCallback != null) {
            propsCallback.run(props);
        }

        final String path = buildPath(props);

        if (path.startsWith("/")) {
            history.push(path);
            return;
        }

        final String parentPath = parentPath();
        history.push(parentPath + path);
    }

    public void handle(RouteProps props) {

    }

    public boolean is(RouteProxy proxy) {
        return proxy != null && proxy.getClass().getName().equals(getClass().getName());
    }

    protected String parentPath() {
        final RouteProxy parent = parent();
        if (parent == null) {
            return "/";
        }
        final String parentPath = parent.path();

        if (parentPath == null) {
            return "/";
        } else {
            if (!parentPath.endsWith("/")) {
                return parentPath + "/";
            } else {
                return parentPath;
            }
        }
    }

    protected String buildPath(Object props) {
        String p = path();
        if (p == null || p.isEmpty()) {
            return "";
        }
        if (p.startsWith("/")) {
            p = p.substring(1);
        }
        final String path = p;
        final String[] parts = path.split("/");

        if (parts.length < 2) {
            return path;
        }

        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < parts.length; i++) {
            if (i > 0) {
                sb.append("/");
            }

            String part = parts[i];

            if (part.startsWith(":")) {
                part = part.substring(1);
            } else {
                sb.append(part);
                continue;
            }

            // Grab value by prop name.
            Object value = React.get(props, part);
            if (value != null) {
                sb.append(String.valueOf(value));
            }
        }

        return sb.toString();
    }
}
