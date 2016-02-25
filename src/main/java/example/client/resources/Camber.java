package example.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.ScriptInjector;

public class Camber {
    static final Resources INSTANCE = GWT.create(Resources.class);

    public static void inject() {

        // jquery and select2
        ScriptInjector.fromString(CamberResourceBundle.INSTANCE.jQuery().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
        ScriptInjector.fromString(CamberResourceBundle.INSTANCE.Select2().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();

        ScriptInjector.fromString(CamberResourceBundle.INSTANCE.WebpackOutput().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
        exposePackedJs();
        ScriptInjector.fromString(INSTANCE.js_react_router().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
//        ScriptInjector.fromString(CamberResourceBundle.INSTANCE.ReactRouter().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
        tapEventPlugin();
    }

    private static native void exposePackedJs() /*-{
        $wnd.React = $wnd.Camber.React;
        $wnd.ReactDOM = $wnd.Camber.ReactDOM;
    }-*/;

    private static native void tapEventPlugin() /*-{
        $wnd.Camber.InjectTapEventPlugin();
    }-*/;
}
