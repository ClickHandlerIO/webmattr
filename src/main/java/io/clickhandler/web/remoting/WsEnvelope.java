package io.clickhandler.web.remoting;

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 * Wasabi Envelope
 */
@JsType(isNative = true)
public interface WsEnvelope {
    @JsProperty(name = "m")
    int getMethod();

    @JsProperty(name = "m")
    void setMethod(int method);

    @JsProperty(name = "i")
    int getId();

    @JsProperty(name = "i")
    void setId(int id);

    @JsProperty(name = "c")
    int getCode();

    @JsProperty(name = "c")
    void setCode(int code);

    @JsProperty(name = "t")
    String getType();

    @JsProperty(name = "t")
    void setType(String type);

    @JsProperty(name = "b")
    String getBody();

    @JsProperty(name = "b")
    void setBody(String body);

    class Factory {
        public static native WsEnvelope create() /*-{
            return {};
        }-*/;

        public static WsEnvelope create(int method, int id, int code, String type, String body) {
            final WsEnvelope envelope = create();
            envelope.setMethod(method);
            envelope.setId(id);
            envelope.setCode(code);
            envelope.setType(type);
            envelope.setBody(body);
            return envelope;
        }
    }

    class Constants {
        // Direction
        public static final int OUT = 0;
        public static final int IN = 1;
        public static final int SUB = 2;
        public static final int USUB = 3;
        public static final int PUSH = 4;
    }
}

