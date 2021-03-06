package io.clickhandler.web.router;

import jsinterop.annotations.JsType;

/**
 *
 */
@JsType(isNative = true)
public interface History {
    void transitionTo(LocationDescriptor location);

    void push(LocationDescriptor location);

    void replace(LocationDescriptor location);

    void go(int n);

    void goBack();

    void goForward();

    String createKey();

    String createPath(LocationDescriptor location);

    String createHref(LocationDescriptor location);
}
