package io.clickhandler.web.event;

import jsinterop.annotations.JsFunction;

/**
 *
 */
@JsFunction
public interface WheelEventHandler {
    void handle(WheelEvent event);
}
