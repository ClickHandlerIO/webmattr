package io.clickhandler.web.event;

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 *
 */
@JsType(isNative = true)
public interface CompositionEvent extends SyntheticEvent {
    @JsProperty
    String getData();
}
