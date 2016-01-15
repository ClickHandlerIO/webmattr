package webmattr.event;

import jsinterop.annotations.JsFunction;

/**
 *
 */
@JsFunction
public interface UIEventHandler {
    void handle(UIEvent event);
}
