package io.clickhandler.web.remoting;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 * Wasabi Envelope
 */
@JsType(isNative = true)
public class WsEnvelope {
    protected WsEnvelope() {
    }

    @JsProperty(name = "m")
    protected native double getM();

    @JsProperty(name = "m")
    protected native void setM(double m);

    @JsProperty(name = "i")
    protected native double getI();

    @JsProperty(name = "i")
    protected native void setI(double i);

    @JsProperty(name = "c")
    protected native double getC();

    @JsProperty(name = "c")
    protected native void setC(double c);

    @JsProperty(name = "t")
    protected native String getT();

    @JsProperty(name = "t")
    protected native void setT(String t);

    @JsProperty(name = "b")
    protected native String getB();

    @JsProperty(name = "b")
    protected native void setB(String c);

    @JsOverlay
    public final double method() {
        return this.getM();
    }

    @JsOverlay
    public final double id() {
        return this.getI();
    }

    @JsOverlay
    public final double code() {
        return this.getC();
    }

    @JsOverlay
    public final String type() {
        return this.getT();
    }

    @JsOverlay
    public final String body() {
        return this.getB();
    }

    @JsOverlay
    public final WsEnvelope method(final double method) {
        this.setM(method);
        return this;
    }

    @JsOverlay
    public final WsEnvelope id(final double id) {
        this.setI(id);
        return this;
    }

    @JsOverlay
    public final WsEnvelope code(final double code) {
        this.setC(code);
        return this;
    }

    @JsOverlay
    public final WsEnvelope type(final String type) {
        this.setT(type);
        return this;
    }

    @JsOverlay
    public final WsEnvelope body(final String body) {
        this.setB(body);
        return this;
    }

    public static class Factory {
        public static native WsEnvelope create() /*-{
            return {};
        }-*/;

        public static native WsEnvelope parse(String json) /*-{
            return $wnd.JSON.parse(json);
        }-*/;

        public static WsEnvelope create(double method, double id, double code, String type, String body) {
            return create()
                .method(method)
                .id(id)
                .code(code)
                .type(type)
                .body(body);
        }
    }

    public static class Constants {
        // Direction
        public static final int OUT = 0;
        public static final int IN = 1;
        public static final int SUB = 2;
        public static final int USUB = 3;
        public static final int PUSH = 4;
    }
}

