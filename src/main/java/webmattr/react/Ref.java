package webmattr.react;

import jsinterop.annotations.JsProperty;
import webmattr.Func;

/**
 *
 */
public class Ref<T> {
    private final static String INIT_PROP_NAME = "$$refinit$$";
    @JsProperty
    final String name;

    private Ref(String name) {
        this.name = "$$" + name;
    }

    public static <T> Ref<T> make(String name) {
        return new Ref<>(name);
    }

    public static <T> Ref<T> make() {
        return new Ref<>(React.createShortUID());
    }

    public void init(ReactComponent $this, Func.Run1<T> callback) {
        final T ref = get($this);
        final Boolean inited = React.get(ref, INIT_PROP_NAME);
        if (inited == null || !inited) {
            React.set(ref, INIT_PROP_NAME, true);
            callback.run(ref);
        }
    }

    public T get(ReactComponent $this) {
        final T value = React.get($this, name);
        if (value != null) {
            return value;
        }
        return React.get(React.get($this, "refs"), name);
    }

    public void get(ReactComponent $this, Func.Run1<T> callback) {
        final T ref = get($this);
        if (callback != null && ref != null) {
            callback.run(ref);
        }
    }

    public void set(ReactComponent $this, T value) {
        React.set($this, name, value);
    }

    public Func.Run1<T> pipe(ReactComponent $this, Func.Run1<T> callback) {
        return (value) -> {
            React.set($this, name, value);
            if (callback != null && value != null) {
                callback.run(value);
            }
        };
    }
}
