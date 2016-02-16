package io.clickhandler.web;

import javax.inject.Inject;
import javax.inject.Provider;

public class NativeProvider<T> implements Provider<T> {
    @Inject
    public NativeProvider() {
    }

    @Override
    public native T get() /*-{
        return {};
    }-*/;
}
