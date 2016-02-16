package io.clickhandler.web.event;

import jsinterop.annotations.JsFunction;

/**
 *
 */
@JsFunction
public interface FormEventHandler {
    void handle(FormEvent event);
}
