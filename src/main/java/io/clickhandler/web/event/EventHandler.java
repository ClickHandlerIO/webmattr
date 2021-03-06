package io.clickhandler.web.event;

import jsinterop.annotations.JsFunction;

/**
 *
 */
@JsFunction
public interface EventHandler<E extends SyntheticEvent> {
    void handle(E event);
}
