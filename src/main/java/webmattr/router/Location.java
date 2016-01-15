package webmattr.router;

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 *
 */
@JsType(isNative = true)
public interface Location {
    @JsProperty(name = "pathname")
    String getPathname();

    @JsProperty
    String getSearch();

    @JsProperty
    Object getState();

    @JsProperty(name = "action")
    Action getAction();

    @JsProperty(name = "query")
    Object getQuery();

    @JsProperty(name = "hash")
    String getHash();

    @JsProperty
    String getKey();

    /**
     *
     */
    public enum Action {
        PUSH,
        POP,
        REPLACE,;
    }
}
