package webmattr.react;

import com.google.gwt.core.client.ScriptInjector;
import jsinterop.annotations.JsMethod;
import webmattr.resources.Resources;

/**
 *
 */
public class React {
    public static final String ACTION_CALLS = "$actionCalls$";
    public static final String ACTION = "$action$";
    public static final String GET_REF = "$getRef$";
    public static final String SET_REF = "$setRef$";
    public static final String EVENT_BUS_REGISTRATIONS = "$eventBusRegistrations$";
    public static final String GET_PROPERTY = "$getProperty$";
    public static final String GET_PROPS = "$getProps$";
    public static final String GET_STATE = "$getState$";
    public static final String PROPS = "props";
    public static final String STATE = "state";

    public static void init() {
        final Resources bundle = Resources.INSTANCE;
        ScriptInjector.fromString(bundle.js_react().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
        ScriptInjector.fromString(bundle.js_react_dom().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
        ScriptInjector.fromString(bundle.js_react_router().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
    }

    /**
     *
     * @param obj
     * @param name
     * @param <T>
     * @return
     */
    public static native <T> T get(Object obj, String name) /*-{
        return obj[name];
    }-*/;

    /**
     *
     * @param obj
     * @param name
     * @param value
     * @param <T>
     * @return
     */
    public static native <T> T set(Object obj, String name, Object value) /*-{
        return obj[name] = value;
    }-*/;

    /**
     *
     * @param obj
     * @param name
     * @param <T>
     * @return
     */
    public static native <T> T delete(Object obj, String name) /*-{
        delete obj[name];
    }-*/;

    /**
     *
     * @param object
     * @return
     */
    @JsMethod(namespace = "React", name = "createClass")
    public static native Object createClass(Object object);

    /**
     *
     * @param component
     * @param props
     * @param children
     * @return
     */
    @JsMethod(namespace = "React", name = "createElement")
    public static native ReactElement createElement(Object component, Object props, Object... children);


    public static native <T> T getProps(Object component) /*-{
        return component && component.props ? component.props : {};
    }-*/;

    public static native <T> T getState(Object component) /*-{
        return component && component.state ? component.state : {};
    }-*/;

    /**
     *
     * @param obj
     * @param nativeClass
     */
    public static void extend(Object obj, String nativeClass) {
        String[] parts = nativeClass.split("[.]");
        extend_(obj, parts);
    }

    /**
     *
     * @param obj
     * @param nativeClass
     */
    public static native void extend_(Object obj, String[] nativeClass) /*-{
        var superClass = $wnd;
        for (var i = 0; i < nativeClass.length; i++) {
            superClass = superClass[nativeClass[i]];
        }
        for (var i in superClass) {
            if (superClass.hasOwnProperty(i)) {
                obj[i] = superClass[i];
            }
        }
    }-*/;

    /**
     *
     * @param nativeObject
     * @param javaPrototype
     * @param <T>
     * @return
     */
    public static native <T> T copy(Object nativeObject, Object javaPrototype) /*-{
        if (!nativeObject) {
            nativeObject = {};
        }
        var extension = Object.create(javaPrototype.__proto__);
        for (var i in nativeObject) {
            if (nativeObject.hasOwnProperty(i)) {
                extension[i] = nativeObject[i];
            }
        }
        return extension;
    }-*/;

    public static native void assign(Object target, Object sources) /*-{
        Object.assign(target, sources);
    }-*/;

    /**
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static native <T> T clone(Object obj) /*-{
        return Object.create(obj.__proto__);
    }-*/;

    /**
     *
     * @return
     */
    public static native String createShortUID() /*-{
        return ("0000" + (Math.random() * Math.pow(36, 4) << 0).toString(36)).slice(-4)
    }-*/;

    public static class PropTypes {
        public static native Object object(boolean required) /*-{
            return required ? $wnd.React.PropTypes.object.isRequired : $wnd.React.PropTypes.object;
        }-*/;

        public static native Object string(boolean required) /*-{
            return required ? $wnd.React.PropTypes.string.isRequired : $wnd.React.PropTypes.string;
        }-*/;
    }
}
