package webmattr.router;

import com.google.gwt.http.client.URL;
import webmattr.Func;
import webmattr.Reflection;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.ArrayList;
import java.util.List;

/**
 * Proxy to an actual RouteComponent and React Router Route configuration object.
 *
 * @author Clay Molocznik
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
    //
    private String memoizedParentPath;

    /**
     *
     */
    public RouteProxy() {
        this(null, null, false);
    }

    /**
     * @param path
     */
    public RouteProxy(String path) {
        this(path, null, false);
    }

    /**
     * @param parent
     */
    public RouteProxy(RouteProxy parent) {
        this(null, parent, false);
    }

    /**
     * @param path
     * @param parent
     */
    public RouteProxy(String path, RouteProxy parent) {
        this(path, parent, false);
    }

    /**
     * @param path
     * @param index
     */
    public RouteProxy(String path, boolean index) {
        this(path, null, index);
    }

    /**
     * @param path
     * @param parent
     * @param index
     */
    public RouteProxy(String path, RouteProxy parent, boolean index) {
        if (path == null) {
            // Default path to class name minus the "Route" suffix.
            String name = getClass().getName().toLowerCase();

            // Remove Route from the end.
            if (name.endsWith("route")) {
                name = name.substring(0, name.length() - 5);
            }

            // Split based off of Java class path.
            String[] parts = name.split("[.]");

            // Pick last name.
            path = parts[parts.length - 1];

            if (path.endsWith("$")) {
                path = path.substring(0, path.length() - 1);

                // Remove 'component' from the end?
                if (path.endsWith("component")) {
                    path = path.substring(0, path.length() - 9);
                }
                // Remove 'page' from the end?
                else if (path.endsWith("page")) {
                    path = path.substring(0, path.length() - 4);
                }
            } else {
                // Handle nested classes.
                parts = path.split("[$]");

                // Pick last part.
                path = parts[parts.length - 1];
            }
        }

        this.path = path;
        this.parent = parent;
        this.index = index;
    }

    private static native boolean smartSet(Object target, String name, Object value) /*-{
        if (value == null) {
            return true;
        }
        var toStringValue = value.toString();

        if (target && name) {
            var defaultValue = target[name];

            switch (typeof(defaultValue)) {
                case "boolean":
                    toStringValue = toStringValue.toLowerCase();
                    target[name] = toStringValue == "true" || toStringValue == "1" || toStringValue == "yes" || toStringValue == "y";
                    break;
                case "string":
                    target[name] = toStringValue;
                    break;
                case "number":
                    if (toStringValue.indexOf(".") > -1) {
                        try {
                            target[name] = parseFloat(toStringValue);
                        } catch (e) {
                            // Failed.
                            return false;
                        }
                    } else {
                        try {
                            target[name] = parseInt(toStringValue);
                        } catch (e) {
                            // Failed.
                            return false;
                        }
                    }
                    break;

                case "undefined":
                    target[name] = toStringValue;
                    break;
            }
        }
        return true;
    }-*/;

    /**
     * @return
     */
    public String path() {
        return path;
    }

    /**
     * @return
     */
    public RouteProxy parent() {
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
    public String parentPath() {
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
        final String path = path();
        final StringBuilder sb = new StringBuilder();

        Reflection.iterate(props, (name, value) -> {
            if (value == null) {
                return;
            }

            if (sb.length() > 0) {
                sb.append("&");
            }

            sb.append(name).append("=").append(URL.encode(String.valueOf(value)));
        });

        if (sb.length() > 0) {
            return path + "?" + sb.toString();
        } else {
            return path;
        }
    }

    public T toArgs(Location location) {
        final T args = argsProvider.get();

        if (location == null) {
            return args;
        }

        final Object query = location.getQuery();
        if (query == null) {
            return args;
        }

        mergeArgs(args, query);
        return args;
    }

    public T toArgs(Object query) {
        final T args = argsProvider.get();

        if (query == null) {
            return args;
        }

        mergeArgs(args, query);
        return args;
    }

    public void mergeArgs(T args, Object query) {
        if (args == null) {
            return;
        }

        Reflection.iterate(query, (name, value) -> {
            // Ignore?
            if (value == null) {
                return;
            }

            // Decode urlencoded string value.
            value = URL.decode(value.toString());

            // Smart set prop.
            smartSet(args, name, value);
        });
    }
}
