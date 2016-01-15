package webmattr.event;


import jsinterop.annotations.JsType;

/**
 *
 */
@JsType(isNative = true)
public interface UIEvent extends SyntheticEvent {
    Number getDetail();


}
