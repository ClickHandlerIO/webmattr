package example.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

/**
 *
 */
public interface Resources extends ClientBundle {
    @Source("react-0.14.7.js")
    TextResource js_react();

    @Source("react-dom-0.14.7.min.js")
    TextResource js_react_dom();

    @Source("react-router.min.js")
    TextResource js_react_router();
}
