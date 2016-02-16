package io.clickhandler.web.event;

import jsinterop.annotations.JsFunction;

/**
 *
 */
@JsFunction
public interface TouchEventHandler {
    void handle(TouchEvent event);
}
