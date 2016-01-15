package webmattr.event;

import jsinterop.annotations.JsFunction;

/**
 *
 */
@JsFunction
public interface ReactEventHandler {
    void handle(SyntheticEvent event);
}
