package webmattr.router;

import jsinterop.annotations.JsType;

/**
 *
 */
@JsType(isNative = true)
public interface History {
    Object createHref(String path, Object query);

    Object createKey();

    Location createLocation();

    Object createPath(String path, Object query);

    void go(int n);

    void goBack();

    void goForward();

    boolean isActive(String pathname, Object query);

    void push(String path);

    void push(Object path);

    void replace(String path);
}
