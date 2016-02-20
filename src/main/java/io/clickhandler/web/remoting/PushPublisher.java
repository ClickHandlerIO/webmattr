package io.clickhandler.web.remoting;

import io.clickhandler.web.Bus;
import jsinterop.annotations.JsFunction;

/**
 *
 */
@JsFunction
public interface PushPublisher<T> {
    void publish(Bus bus, Bus.TypeName<T> name, Bus.TypeName<T> scopedName, String json);
}
