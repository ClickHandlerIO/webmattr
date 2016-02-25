package example.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;


public interface CamberResourceBundle extends ClientBundle {
    CamberResourceBundle INSTANCE = GWT.create(CamberResourceBundle.class);


    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // JS Resources
    ////////////////////////////////////////////////////////////////////////////////////////////////////

    @Source("js/webpack-output.js")
    TextResource WebpackOutput();

    @Source("js/react-router.min.js")
    TextResource ReactRouter();

    @Source("js/jquery-2.2.1.min.js")
    TextResource jQuery();

    @Source("js/select2.full.min.js")
    TextResource Select2();
}
