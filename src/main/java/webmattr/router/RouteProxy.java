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

    public RouteProxy() {
        this.path = path();
    }

    protected String path() {
        return "";
    }

    private static native void iterateProps(Object props, Func.Run2<String, Object> callback) /*-{
        if (!props || !callback) {
            return;
        }

        for (p in props) {
            if (props.hasOwnProperty(p)) {
                callback(p, props[p]);
            }
        }
    }-*/;

    public String getPath() {
        return path;
    }

    public Provider<T> getArgsProvider() {
        return argsProvider;
    }

    public void go() {
        history.push(getPath());
    }

    public void go(Func.Run1<T> propsCallback) {
        final T props = getArgsProvider().get();
        if (propsCallback != null) {
            propsCallback.run(props);
        }

        final String pathname = new PathBuilder(path).build(props);

        history.push(pathname);
    }

    public void handle(RouteProps props) {

    }

    private class PathBuilder {
        private String spec;

        public PathBuilder(String spec) {
            this.spec = spec;
        }

        public String build(Object props) {
            final String[] parts = spec.split("/");

            if (parts.length < 2) {
                return spec;
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

                Object value = React.get(props, part);
                if (value != null) {
                    sb.append(String.valueOf(value));
                }
            }

            return sb.toString();
        }
    }
}
