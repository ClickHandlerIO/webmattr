package io.clickhandler.web.event;

import jsinterop.annotations.JsFunction;

/**
 *
 */
@JsFunction
public interface CompositionEventHandler {
    void handle(CompositionEvent event);
}
