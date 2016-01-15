package webmattr.event;

import jsinterop.annotations.JsFunction;

/**
 *
 */
@JsFunction
public interface CompositionEventHandler {
    void handle(CompositionEvent event);
}
