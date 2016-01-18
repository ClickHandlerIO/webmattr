package webmattr.io;

import com.google.gwt.core.client.ScriptInjector;
import webmattr.resources.Resources;

/**
 *
 */
public class ZLib {
    public static void init() {
        ScriptInjector.fromString(Resources.INSTANCE.js_pako().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
    }
}
