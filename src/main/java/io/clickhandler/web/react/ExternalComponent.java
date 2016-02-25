package io.clickhandler.web.react;

import io.clickhandler.web.Func;
import io.clickhandler.web.Jso;
import io.clickhandler.web.Reflection;
import io.clickhandler.web.dom.DOM;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 *
 * @param <P>
 */
public abstract class ExternalComponent<P> {
    public ExternalComponent() {
    }

    public native P createProps() /*-{
        return {};
    }-*/;

    protected void initProps(P props) {
    }

    protected abstract ReactClass<P> reactClass();

    protected P defaultProps() {
        final P props = createProps();
        initProps(props);
        Reflection.assign(props, reactClass().getDefaultProps());
        applyKey(props);
        return props;
    }

    protected void applyKey(P props) {
        Object key = Reflection.get(props, "key");
        if (key == null) {
            Reflection.set(props, "key", ChildCounter.get().newKey());
        }
    }

    public ReactElement $() {
        return React.createElement(reactClass(), defaultProps());
    }

    public ReactElement $(Object... children) {
        return React.createElement(reactClass(), defaultProps(), children);
    }

    public ReactElement $(Func.Run1<P> propsCallback) {
        P props = defaultProps();
        if (props == null) props = Jso.create();
        if (propsCallback != null) {
            propsCallback.run(props);
        }
        return React.createElement(reactClass(), props);
    }

    public ReactElement $(Func.Run1<P> propsCallback, Func.Run1<DOM.ChildList> childCallback) {
        P props = defaultProps();
        if (props == null) props = Jso.create();
        if (propsCallback != null) {
            propsCallback.run(props);
        }

        final DOM.ChildList childList = new DOM.ChildList();
        if (childCallback != null) {
            childCallback.run(childList);
        }

        return React.createElement(reactClass(), props, childList.toArray());
    }

    public ReactElement $(Func.Run1<P> propsCallback, Object... children) {
        P props = defaultProps();
        if (props == null) props = Jso.create();
        if (propsCallback != null) {
            propsCallback.run(props);
        }
        return React.createElement(reactClass(), props, children);
    }

    public ReactElement $$(Func.Run1<DOM.ChildList> childCallback) {
        P props = defaultProps();
        if (props == null) props = Jso.create();
        final DOM.ChildList childList = new DOM.ChildList();
        if (childCallback != null) {
            childCallback.run(childList);
        }
        return React.createElement(reactClass(), props, childList.toArray());
    }
}
