package io.clickhandler.web.router;

import jsinterop.annotations.JsProperty;

/**
 *
 */
public class LocationDescriptor {
    @JsProperty
    public String pathname;
    @JsProperty
    public Object search;
    @JsProperty
    public Object query;
    @JsProperty
    public Object state;

    public LocationDescriptor() {
    }

    public LocationDescriptor pathname(String pathname) {
        this.pathname = pathname;
        return this;
    }

    public LocationDescriptor search(Object search) {
        this.search = search;
        return this;
    }

    public LocationDescriptor query(Object query) {
        this.query = query;
        return this;
    }

    public LocationDescriptor state(Object state) {
        this.state = state;
        return this;
    }
}
