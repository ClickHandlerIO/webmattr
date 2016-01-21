package webmattr.router;

import webmattr.Func;
import webmattr.Reflection;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.ArrayList;
import java.util.List;

/**
 * Proxy to an actual RouteComponent and Route configuration object.
 */
public class RouteProxy<T> {
    // Inject history.
    @Inject
    History history;
    // Inject Args Provider.
    @Inject
    Provider<T> argsProvider;

    // Path of this proxy.
    private String path;
    // Is it an "index" or "/" route of it's parent?
    private boolean index;
    // Parent RouteProxy.
    private RouteProxy parent;

    private String memoizedParentPath;

    /**
     *
     */
    public RouteProxy() {
    }

    /**
     * @param path
     */
    public RouteProxy(String path) {
        this.path = path;
    }

    /**
     * @param parent
     */
    public RouteProxy(RouteProxy parent) {
        this.parent = parent;
    }

    /**
     * @param path
     * @param parent
     */
    public RouteProxy(String path, RouteProxy parent) {
        this.path = path;
        this.parent = parent;
    }

    /**
     * @param path
     * @param index
     */
    public RouteProxy(String path, boolean index) {
        this.path = path;
        this.index = index;
    }

    /**
     * @param path
     * @param parent
     * @param index
     */
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
        Reflection.assign(args, nextState.getParams());
        return onEnter(args, nextState, replaceState);
    }

    /**
     * @param nextState
     * @param replaceState
     */
    protected boolean onEnter(T args, RouteProps nextState, ReplaceStateFunction replaceState) {
        return true;
    }

    /**
     * @return
     */
    protected Object onLeave() {
        return null;
    }

    /**
     * @return
     */
    public Provider<T> getArgsProvider() {
        return argsProvider;
    }

    /**
     *
     */
    public void go() {
        go(null);
    }

    /**
     * @param propsCallback
     */
    public void go(Func.Run1<T> propsCallback) {
        final T props = getArgsProvider().get();
        if (propsCallback != null) {
            propsCallback.run(props);
        }

        final String path = buildPath(props);
        String pathSpec = path();
        if (pathSpec == null) {
            pathSpec = "";
        }

        // Do we have an absolute path?
        if (pathSpec.startsWith("/") || path.startsWith("/")) {
            history.push(path.startsWith("/") ? path : "/" + path);
            return;
        }

        // Get parent path.
        final String parentPath = parentPath();

        if (path.isEmpty()) {
            history.push(parentPath);
        } else if (parentPath.endsWith("/")) {
            history.push(parentPath + path);
        } else {
            history.push(parentPath + "/" + path);
        }
    }

    /**
     * @param props
     */
    public void handle(RouteProps props) {

    }

    /**
     * @param proxy
     * @return
     */
    public boolean is(RouteProxy proxy) {
        return proxy != null && proxy.getClass().getName().equals(getClass().getName());
    }

    /**
     * @return
     */
    protected String parentPath() {
        if (memoizedParentPath != null) {
            return memoizedParentPath;
        }

        final List<RouteProxy> ancesters = new ArrayList<>();

        RouteProxy parent = parent();
        if (parent == null) {
            return "";
        }

        while (parent != null) {
            ancesters.add(0, parent);

            String parentPath = parent.path();
            if (parentPath == null) {
                parentPath = "";
            }

            if (parentPath.startsWith("/")) {
                break;
            }

            parent = parent.parent();
        }

        final StringBuilder sb = new StringBuilder();

        char lastChar = ' ';
        for (int i = 0; i < ancesters.size(); i++) {
            final RouteProxy ancestor = ancesters.get(i);
            String parentPath = ancestor.path();
            if (parentPath == null) {
                parentPath = "";
            }
            parentPath = parentPath.trim();

            if (parentPath.length() > 1 && parentPath.endsWith("/")) {
                parentPath = parentPath.substring(0, parentPath.length() - 1);
            }

            if (parentPath.isEmpty()) {
                continue;
            }

            if (lastChar != '/' && !parentPath.startsWith("/")) {
                sb.append("/");
            }
            sb.append(parentPath);

            lastChar = parentPath.charAt(parentPath.length() - 1);
        }

        return memoizedParentPath = sb.toString();
    }

    /**
     * @param props
     * @return
     */
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
            String part = parts[i].trim();
            if (part.isEmpty()) {
                continue;
            }

            if (i > 0) {
                sb.append("/");
            }

            if (part.startsWith(":")) {
                part = part.substring(1);
            } else {
                sb.append(part);
                continue;
            }

            // Grab value by prop name.
            Object value = Reflection.get(props, part);
            if (value != null) {
                sb.append(String.valueOf(value));
            }
        }

        return sb.toString();
    }
}
