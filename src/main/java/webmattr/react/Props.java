package webmattr.react;

import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import webmattr.Func;

/**
 *
 */
@JsType
public class Props<T> {
    @JsProperty
    public Object children;

    @JsProperty
    public Object key;

    @JsProperty
    public Object ref;

    @JsIgnore
    private Ref<T> _ref;

    public Props() {
    }

    @JsIgnore
    public Props<T> children(Object children) {
        this.children = children;
        return this;
    }

    @JsIgnore
    public Props<T> key(Object key) {
        this.key = key;
        return this;
    }

    @JsIgnore
    public Props<T> ref(Func.Run1<T> ref) {
        this.ref = ref;
        return this;
    }

    @JsIgnore
    public Props<T> ref(String ref) {
        this.ref = ref;
        return this;
    }

    @JsIgnore
    public Props<T> ref(Ref<T> ref) {
        this.ref = ref.name;
        return this;
    }
}
