package io.clickhandler.web.router;

import io.clickhandler.web.react.ReactElement;
import io.clickhandler.web.react.React;

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
