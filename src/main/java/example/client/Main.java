package example.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.ScriptInjector;
import dagger.Component;
import dagger.Module;
import dagger.Provides;
import elemental.client.Browser;
import io.clickhandler.web.dom.DOM;
import io.clickhandler.web.dom.ReactDOM;
import io.clickhandler.web.react.*;
import io.clickhandler.web.resources.Resources;
import io.clickhandler.web.router.History;
import io.clickhandler.web.router.ReactRouter;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 *
 */
public class Main implements EntryPoint {
    public static native <T> T create() /*-{
        return {};
    }-*/;

    public static native <T> T create(JavaScriptObject obj) /*-{
        return obj;
    }-*/;

    public static native Props createProps() /*-{
        return {};
    }-*/;

    @Override
    public void onModuleLoad() {
        ScriptInjector.fromString(Resources.INSTANCE.js_react().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
        ScriptInjector.fromString(Resources.INSTANCE.js_react_dom().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
        ScriptInjector.fromString(Resources.INSTANCE.js_react_router().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();

        ReactDOM.render(MyComponent.instance.hiComponent().$(
            $ -> {
                $.setName("Hello");
            }
        ), Browser.getDocument().getElementById("app"));
    }

    @Singleton
    @Component(modules = M.class)
    public interface MyComponent {
        MyComponent instance = DaggerMain_MyComponent.create();

        HiComponent hiComponent();
    }

    @Module
    public static class M {
        public static M create() {
            return new M();
        }

        @Provides
        HiComponent.Props props() {
            return Main.create();
        }

        @Provides
        HiComponent.State state() {
            return Main.create();
        }

        @Provides
        History history() {
            return ReactRouter.getHashHistory();
        }
    }

//    @Singleton
//    public static class HiComponent extends io.clickhandler.web.react.Component<HiComponent.Props, HiComponent.State> {
//        @Inject
//        public HiComponent() {
//        }
//
//        @Override
//        protected native Props getDefaultProps(ReactComponent<Props, State> $this) /*-{
//            return {};
//        }-*/;
//
//        @Override
//        protected ReactElement render(ReactComponent<Props, State> $this, Props props, State state) {
//            console.log(props);
//            console.log(state);
//            return DOM.div("Hi");
//        }
//
//        @JsType(isNative = true)
//        public interface Props {
//            @JsProperty
//            String getName();
//            @JsProperty
//            void setName(String name);
//        }
//
//        @JsType(isNative = true)
//        public interface State {
//
//        }
//    }

    @Singleton
    public static class HiComponent extends io.clickhandler.web.react.Component<HiComponent.Props, HiComponent.State> {
        @Inject
        public HiComponent() {
        }

        @Override
        protected ReactElement render(ReactComponent<Props, State> $this, Props props, State state) {
            console.log(props);
            console.log(state);
            return DOM.div("Hi", DOM.button($ -> {
                $.onClick(event -> {
                    $this.forceUpdate();
                });
            }, "Click Me"));
        }

        @Override
        protected void componentDidUpdate(ReactComponent<Props, State> $this, Props nextProps, State nextState) {
            super.componentDidUpdate($this, nextProps, nextState);
            console.log(nextProps);
        }

        @JsType(isNative = true)
        public interface Props {
            @JsProperty
            String getName();

            @JsProperty
            void setName(String name);
        }

        @JsType(isNative = true)
        public interface State {

        }
    }

}
