package webmattr.ws;

import jsinterop.annotations.JsProperty;

/**
 * Wasabi Envelope
 */
public class WsEnvelope {
    // Direction
    public static final int IN = 0;
    public static final int OUT = 1;

    // Encoding
    public static final int NONE = 0;
    public static final int GZIP = 1;

    // Format
    public static final int JSON = 0;
    public static final int MSGPACK = 1;
    public static final int XML = 2;
    public static final int RAW = 9;

    @JsProperty(name = "d")
    private int in;
    @JsProperty(name = "i")
    private int id;
    @JsProperty(name = "s")
    private int sequence;
    @JsProperty(name = "c")
    private int code;
    @JsProperty(name = "e")
    private int encoding;
    @JsProperty(name = "f")
    private int format;
    @JsProperty(name = "t")
    private String type;
    @JsProperty(name = "p")
    private String payload;

    public WsEnvelope() {
    }

    public WsEnvelope(int in, int id, int code, String type, String payload) {
        this.in = in;
        this.id = id;
        this.code = code;
        this.type = type;
        this.payload = payload;
    }

    public WsEnvelope(int in, int id, int code, int encoding, int format, String type, String payload) {
        this.in = in;
        this.id = id;
        this.code = code;
        this.encoding = encoding;
        this.format = format;
        this.type = type;
        this.payload = payload;
    }

    public int isIn() {
        return in;
    }

    public void setIn(int in) {
        this.in = in;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}

