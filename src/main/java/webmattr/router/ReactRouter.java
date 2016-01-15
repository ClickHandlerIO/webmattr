package webmattr.router;

import webmattr.react.React;
import webmattr.react.ReactElement;

/**
 *
 */
public class ReactRouter {
    public static native Object getRouter() /*-{
        return $wnd.ReactRouter.Router;
    }-*/;

    public static native History getBrowserHistory() /*-{
        return $wnd.ReactRouter.browserHistory;
    }-*/;

    public static native History getHashHistory() /*-{
        return $wnd.ReactRouter.hashHistory;
    }-*/;

    public static ReactElement create(RouterProps props) {
        return React.createElement(getRouter(), props);
    }
}
