package webmattr.react;

import jsinterop.annotations.JsFunction;

/**
 *
 */
@JsFunction
public interface ReactFactory<P extends Props> {
    ReactElement create(P props, ReactElement... children);
}
