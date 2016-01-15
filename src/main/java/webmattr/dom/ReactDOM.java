package webmattr.dom;

import elemental.dom.Element;
import jsinterop.annotations.JsMethod;
import webmattr.react.ReactElement;

/**
 *
 */
public class ReactDOM {
    @JsMethod(namespace = "ReactDOM")
    public static native ReactElement render(ReactElement obj, Element element);
}
