package io.clickhandler.web.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

/**
 *
 */
public interface Resources extends ClientBundle {
    Resources INSTANCE = GWT.create(Resources.class);

    @Source("react-0.14.6.min.js")
    TextResource js_react();

    @Source("react-dom-0.14.6.min.js")
    TextResource js_react_dom();

    @Source("react-router.min.js")
    TextResource js_react_router();
}
