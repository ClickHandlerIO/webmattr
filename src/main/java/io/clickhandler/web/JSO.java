package io.clickhandler.web;

/**
 *
 */
public interface Jso {
    static <T> T create() {
        return Native.create();
    }

    static <T> T create(Class<T> cls) {
        return Native.create();
    }

    static <T> T create(Class<T> cls, Func.Run1<T> callback) {
        final T jso = create();
        if (callback != null) callback.run(jso);
        return jso;
    }

    class Native {
        public static native <T> T create() /*-{
            return {};
        }-*/;
    }
}
