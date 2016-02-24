package example.client;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

/**
 *
 */
@JsType(isNative = true, name = "Object", namespace = JsPackage.GLOBAL)
public class JsObject {
    public static native String[] keys(JsObject obj);
    public native boolean hasOwnProperty(String prop);
}
