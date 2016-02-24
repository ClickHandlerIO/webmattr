package example.client;

import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

import javax.inject.Inject;

/**
 *
 */
@JsType(isNative = true, namespace = "client", name = "Props")
public abstract class Props extends JsObject {
    protected Props() {
    }

    @JsProperty
    public native Object getKey();

    @JsProperty
    public native void setKey(Object key);
}
