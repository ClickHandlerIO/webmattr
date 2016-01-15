package webmattr.ws;

import jsinterop.annotations.JsProperty;

/**
 *
 */
public class WsEnvelope {
    @JsProperty(name = "d")
    private boolean in;
    @JsProperty(name = "i")
    private int id;
    @JsProperty(name = "c")
    private int code;
    @JsProperty(name = "t")
    private String type;
    @JsProperty(name = "p")
    private String payload;

    public WsEnvelope() {
    }

    public WsEnvelope(boolean in, int id, int code, String type, String payload) {
        this.in = in;
        this.id = id;
        this.code = code;
        this.type = type;
        this.payload = payload;
    }

    public boolean isIn() {
        return in;
    }

    public void setIn(boolean in) {
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

