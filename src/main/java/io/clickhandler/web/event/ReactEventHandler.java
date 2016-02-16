package io.clickhandler.web.event;

import jsinterop.annotations.JsFunction;

/**
 *
 */
@JsFunction
public interface ReactEventHandler {
    void handle(SyntheticEvent event);
}
