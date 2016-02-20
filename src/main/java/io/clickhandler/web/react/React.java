package io.clickhandler.web.react;

import com.google.gwt.core.client.ScriptInjector;
import jsinterop.annotations.JsMethod;
import io.clickhandler.web.resources.Resources;

/**
 *
 */
public class React {
    public static final String BUS = "$bus$";
    public static final String ACTION_CALLS = "$actionCalls$";
    public static final String ACTION = "$action$";
    public static final String GET_REF = "$getRef$";
    public static final String SET_REF = "$setRef$";
    public static final String HANDLER_REGISTRATIONS = "$handlerReg$";
    public static final String GET_PROPERTY = "$getProperty$";
    public static final String GET_PROPS = "$getProps$";
    public static final String GET_STATE = "$getState$";
    public static final String PROPS = "props";
    public static final String STATE = "state";
    public static final String REGISTER = "$register$";
    public static final String SUBSCRIBE_1 = "$subscribe1$";
    public static final String SUBSCRIBE_2 = "$subscribe2$";
    public static final String SUBSCRIBE_3 = "$subscribe3$";
    public static final String PUBLISH_1 = "$publish1$";
    public static final String PUBLISH_2 = "$publish2$";
    public static final String PUBLISH_3 = "$publish3$";
    public static final String CLEANUP = "$cleanup$";
    public static final String DISPATCH = "$dispatch$";

    public static void init() {
        final Resources bundle = Resources.INSTANCE;
        ScriptInjector.fromString(bundle.js_react().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
        ScriptInjector.fromString(bundle.js_react_dom().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
        ScriptInjector.fromString(bundle.js_react_router().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
    }

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

    public static class PropTypes {
        public static native Object object(boolean required) /*-{
            return required ? $wnd.React.PropTypes.object.isRequired : $wnd.React.PropTypes.object;
        }-*/;

        public static native Object string(boolean required) /*-{
            return required ? $wnd.React.PropTypes.string.isRequired : $wnd.React.PropTypes.string;
        }-*/;
    }
}
