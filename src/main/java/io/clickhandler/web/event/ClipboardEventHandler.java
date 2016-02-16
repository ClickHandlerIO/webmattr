package io.clickhandler.web.event;

import jsinterop.annotations.JsFunction;

/**
 *
 */
@JsFunction
public interface ClipboardEventHandler {
    void handle(ClipboardEvent event);
}
