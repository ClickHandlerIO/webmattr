package example.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.ScriptInjector;
import dagger.Component;
import dagger.Module;
import dagger.Provides;
import elemental.client.Browser;
import example.client.resources.Resources;
import io.clickhandler.web.Bus;
import io.clickhandler.web.dom.DOM;
import io.clickhandler.web.dom.ReactDOM;
import io.clickhandler.web.react.ReactComponent;
import io.clickhandler.web.react.ReactElement;
import io.clickhandler.web.router.History;
import io.clickhandler.web.router.ReactRouter;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Date;

/**
 *
 */
public class Main implements EntryPoint {
    static final Resources INSTANCE = GWT.create(Resources.class);

    static void init() {
        final Resources bundle = INSTANCE;
        ScriptInjector.fromString(bundle.js_react().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
        ScriptInjector.fromString(bundle.js_react_dom().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
        ScriptInjector.fromString(bundle.js_react_router().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
    }

    @Override
    public void onModuleLoad() {
        init();

        MyComponent.instance.hiComponent().getReactClass();
        Browser.getWindow().getConsole().log(MyComponent.instance.hiComponent());

//        // Create Router.
//        final ReactElement router =
//            ReactRouter.create(new RouterProps()
//                .history(ReactRouter.getHashHistory())
//                .routes(new Route()
//                    .path("/")
//                    .childRoutes()
//                ));
//
//        Try.run(beforeRender);
//
//        // Render.
//        ReactDOM.render(router, Browser.getDocument().getElementById(elementId));

        ReactDOM.render(MyComponent.instance.hiComponent().$(
            $ -> $.name("Fluent Setter")
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
//            console.log(props);
            console.log(state);
            console.log($this);

//            console.log($this.getBus());

            return DOM.div("Hi", DOM.button($ -> {
                $.onClick(event -> {
                    $this.setState(s -> s.setValue(new Date().getTime() + ""));
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

            @JsOverlay
            default Props name(String name) {
                setName(name);
                return this;
            }

            class Event {
                public final static Bus.TypeName<Props> NAME = new Bus.TypeName<>();
            }
        }

        @JsType(isNative = true)
        public interface State {
            @JsProperty
            String getValue();

            @JsProperty
            void setValue(String value);
        }
    }
}
