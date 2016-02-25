package io.clickhandler.web.react;

import elemental.client.Browser;
import elemental.dom.Document;
import elemental.html.Console;
import elemental.html.Window;
import io.clickhandler.web.*;
import io.clickhandler.web.dom.CSSProps;
import io.clickhandler.web.dom.DOM;
import io.clickhandler.web.router.History;
import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

import javax.inject.Inject;

/**
 * @param <P>
 * @param <S>
 */
@JsType
public abstract class Component<P, S> {
    @JsProperty
    protected final Console console = Browser.getWindow().getConsole();
    @JsProperty
    protected final Document document = Browser.getDocument();
    @JsProperty
    protected final Window window = Browser.getWindow();
    @JsProperty(name = "componentDidUpdate")
    public Func.Run2<P, S> componentDidUpdate = Func.bind(this::componentDidUpdate0);
    @JsProperty
    public Func.Run componentDidMount = Func.bind(this::componentDidMount0);
    @JsProperty
    public Func.Run componentWillUnmount = Func.bind(this::componentWillUnmount0);
    @JsProperty
    public Func.Run1<P> componentWillReceiveProps = Func.bind(this::componentWillReceiveProps0);
    @JsProperty
    public Func.Call<P> getDefaultProps = Func.bind(this::getDefaultProps);
    @JsProperty
    public Func.Run2<P, S> componentWillUpdate = Func.bind(this::componentWillUpdate0);
    @JsProperty
    public Func.Call<S> getInitialState = Func.bind(this::getInitialState);
    @JsProperty
    public Func.Call2<Boolean, P, S> shouldComponentUpdate = Func.bind(this::shouldComponentUpdate0);
    @JsProperty(name = "render")
    public Func.Call<ReactElement> render = Func.bind(this::render0);
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // Defaults
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @JsProperty
    public String displayName = "";
    @JsProperty
    public ContextTypes contextTypes = new ContextTypes();
    @Inject
    Bus bus;
    @JsProperty
    public Func.Run componentWillMount = Func.bind(this::componentWillMount0);
    @Inject
    History history;
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @JsProperty
    private Object reactClass;

    public Component() {
//        Reflection.set(this, "render", Func.bind(this::render0));
//        Reflection.set(this, "componentDidUpdate", Func.bind(this::componentDidUpdate0));
//        Reflection.set(this, "componentDidMount", Func.bind(this::componentDidMount0));
//        Reflection.set(this, "componentWillUnmount", Func.bind(this::componentWillUnmount0));
//        Reflection.set(this, "componentWillReceiveProps", Func.bind(this::componentWillReceiveProps0));
//        Reflection.set(this, "getDefaultProps", Func.bind(this::getDefaultProps));
//        Reflection.set(this, "componentWillUpdate", Func.bind(this::getDefaultProps));
//        Reflection.set(this, "getInitialState", Func.bind(this::getInitialState));
//        Reflection.set(this, "shouldComponentUpdate", Func.bind(this::shouldComponentUpdate0));
//        Reflection.set(this, "componentWillMount", Func.bind(this::componentWillMount0));

        displayName = getDisplayName();
        addContextTypes(contextTypes);
    }

    @JsIgnore
    public static <T> T create() {
        return Jso.create();
    }

    @JsIgnore
    public static <T> T create(Class<T> cls) {
        return Jso.create();
    }

    @JsIgnore
    public static <T> T create(Class<T> cls, Func.Run1<T> callback) {
        return Jso.create(cls, callback);
    }

    @JsIgnore
    protected CSSProps css() {
        return new CSSProps();
    }

    @JsIgnore
    protected String getDisplayName() {
        return getClass().getSimpleName();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // Build Context Types Object.
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @JsIgnore
    protected void addContextTypes(ContextTypes contextTypes) {
    }

    @JsIgnore
    public P getDefaultProps(ReactComponent<P, S> $this) {
        return createProps();
    }

    @JsIgnore
    public S getInitialState(ReactComponent<P, S> $this) {
        return createState();
    }

    @JsIgnore
    protected native P createProps() /*-{
        return {};
    }-*/;

    @JsIgnore
    protected native S createState() /*-{
        return {};
    }-*/;

    /**
     * Invoked before rendering when new props or state are being received.
     * This method is not called for the initial render or when forceUpdate is used.
     * Use this as an opportunity to return false when you're certain that the transition to the new props and state will not require a component update.
     * By default, shouldComponentUpdate always returns true to prevent subtle bugs when state is mutated in place,
     * but if you are careful to always treat state as immutable and to read only from props and state in render()
     * then you can override shouldComponentUpdate with an implementation that compares the old props and state to their replacements.
     * <p/>
     * If performance is a bottleneck, especially with dozens or hundreds of components, use shouldComponentUpdate to speed up your app.
     *
     * @param nextProps the props object that the component will receive
     * @param nextState the state object that the component will receive
     */
    @JsMethod(name = "_____1")
    protected boolean shouldComponentUpdate0(final ReactComponent<P, S> $this, P nextProps, S nextState) {
        return shouldComponentUpdate($this, nextProps, nextState);
    }

    @JsMethod(name = "_____2")
    protected boolean shouldComponentUpdate(final ReactComponent<P, S> $this, P nextProps, S nextState) {
        return true;
    }

    /**
     * The render() method is required. When called, it should examine this.props and this.state and return a single child component.
     * This child component can be either a virtual representation of a native DOM component (such as <div /> or React.DOM.div())
     * or another composite component that you've defined yourself.
     * The render() function should be pure, meaning that it does not modify component state, it returns the same result each time it's invoked,
     * and it does not read from or write to the DOM or otherwise interact with the browser (e.g., by using setTimeout).
     * If you need to interact with the browser, perform your work in componentDidMount() or the other lifecycle methods instead.
     * Keeping render() pure makes server rendering more practical and makes components easier to think about.
     */
    @JsMethod(name = "_____3")
    protected ReactElement render0(final ReactComponent<P, S> $this) {
        P props = $this.getProps();
        S state = $this.getState();
        ChildCounter.get().scope();
        try {
            if (props != null) {
                // Fix children not having a "key" set.
                Object children = Reflection.get(props, "children");
                if (children != null) {
                    Object key = Reflection.get(children, "key");

                    if (key == null) {
                        // Create a new key.
                        key = ChildCounter.get().newKey();
                        // Try to set it on children.
                        Reflection.set(children, "key", key);

                        // Test that key was set properly.
                        if (Reflection.get(children, "key") != key) {
                            // Copy to new "children" object.
                            final Object newChildren = new Object();
                            Reflection.assign(newChildren, children);
                            // Set key.
                            Reflection.set(newChildren, "key", key);
                            // Set new "children" on props.
                            Reflection.set(props, "children", newChildren);
                        }
                    }
                }
            }
            return render($this, props, state);
        } finally {
            ChildCounter.get().pop();
        }
    }

    @JsMethod(name = "_____4")
    protected abstract ReactElement render(final ReactComponent<P, S> $this, P props, S state);

    @JsMethod(name = "_____5")
    private void componentWillMount0(final ReactComponent<P, S> $this) {
        Reflection.set($this, React.BUS, new BusDelegate(bus));
        componentWillMount($this, $this.getProps(), $this.getState());
    }

    @JsMethod(name = "_____6")
    protected void componentWillMount(final ReactComponent<P, S> $this, P props, S state) {

    }

    @JsMethod(name = "_____7")
    private void componentDidMount0(final ReactComponent<P, S> $this) {
        componentDidMount($this);
    }

    /**
     * Invoked immediately after rendering occurs.
     * At this point in the lifecycle, the component has a DOM representation which you can access via the rootNode argument or by calling this.getDOMNode().
     * If you want to integrate with other JavaScript frameworks, set timers using setTimeout or setInterval,
     * or send AJAX requests, perform those operations in this method.
     */
    @JsMethod(name = "_____8")
    protected void componentDidMount(final ReactComponent<P, S> $this) {
    }

    @JsMethod(name = "_____9")
    private void componentWillReceiveProps0(final ReactComponent<P, S> $this, P nextProps) {
        componentWillReceiveProps($this, nextProps);
    }

    @JsMethod(name = "_____10")
    protected void componentWillReceiveProps(final ReactComponent<P, S> $this, P nextProps) {

    }

    @JsMethod(name = "_____11")
    protected void componentWillUpdate0(final ReactComponent<P, S> $this, P nextProps, S nextState) {

    }

    @JsMethod(name = "_____12")
    private void componentDidUpdate0(final ReactComponent<P, S> $this, P nextProps, S nextState) {
        componentDidUpdate($this, nextProps, nextState);
    }

    @JsMethod(name = "_____13")
    protected void componentDidUpdate(final ReactComponent<P, S> $this, P nextProps, S nextState) {
    }

    @JsMethod(name = "_____14")
    protected void componentWillUnmount0(final ReactComponent<P, S> $this) {
        try {
            $this.cleanup();
        } finally {
            componentWillUnmount($this);
        }
    }

    @JsMethod(name = "_____15")
    protected void componentWillUnmount(final ReactComponent<P, S> $this) {
    }

    @JsMethod(name = "_____16")
    public ReactClass getReactClass() {
        if (reactClass == null) {
            reactClass = React.createClass(this);
        }
        return (ReactClass) reactClass;
    }

    @JsMethod(name = "_____17")
    protected P props() {
        // Create Props.
        final P props = createProps();

        // Set key manually.
        Object key = Reflection.get(props, "key");
        if (key == null) {
            Reflection.set(props, "key", ChildCounter.get().newKey());
        }

        // Return props.
        return props;
    }

    /**
     * @param children
     * @return
     */
    @JsMethod(name = "_____18")
    public ReactElement $(Object... children) {
        return React.createElement(getReactClass(), props(), children);
    }

    /**
     * @return
     */
    @JsMethod(name = "_____19")
    public ReactElement $() {
        return React.createElement(getReactClass(), props());
    }

    /**
     * @param props
     * @return
     */
    @JsMethod(name = "_____20")
    public ReactElement $(P props) {
        return React.createElement(getReactClass(), props);
    }

    /**
     * @param props
     * @param childCallback
     * @return
     */
    @JsMethod(name = "_____21")
    public ReactElement $(P props, Func.Run1<DOM.ChildList> childCallback) {
        final DOM.ChildList childList = new DOM.ChildList();
        if (childCallback != null) {
            childCallback.run(childList);
        }
        return React.createElement(getReactClass(), props, childList.toArray());
    }

    /**
     * @param propsCallback
     * @param childCallback
     * @return
     */
    @JsMethod(name = "_____22")
    public ReactElement $(Func.Run1<P> propsCallback, Func.Run1<DOM.ChildList> childCallback) {
        final P props = props();
        if (propsCallback != null) {
            propsCallback.run(props);
        }
        final DOM.ChildList childList = new DOM.ChildList();
        if (childCallback != null) {
            childCallback.run(childList);
        }
        return React.createElement(getReactClass(), props, childList.toArray());
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // Factory Methods
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * @param callback
     * @return
     */
    @JsMethod(name = "_____23")
    public ReactElement $(Func.Run2<P, DOM.ChildList> callback) {
        final P props = props();
        final DOM.ChildList childList = new DOM.ChildList();
        if (callback != null) {
            callback.run(props, childList);
        }
        return React.createElement(getReactClass(), props, childList.toArray());
    }

    /**
     * @param propsCallback
     * @return
     */
    @JsMethod(name = "_____24")
    public ReactElement $(Func.Run1<P> propsCallback) {
        final P props = props();
        if (propsCallback != null) {
            propsCallback.run(props);
        }
        return React.createElement(getReactClass(), props);
    }

    /**
     * @param propsCallback
     * @param children
     * @return
     */
    @JsMethod(name = "_____25")
    public ReactElement $(Func.Run1<P> propsCallback, Object... children) {
        final P props = props();
        if (propsCallback != null) {
            propsCallback.run(props);
        }
        return React.createElement(getReactClass(), props, children);
    }

    /**
     * @param childCallback
     * @return
     */
    @JsMethod(name = "_____26")
    public ReactElement $$(Func.Run1<DOM.ChildList> childCallback) {
        final P props = props();
        final DOM.ChildList childList = new DOM.ChildList();
        if (childCallback != null) {
            childCallback.run(childList);
        }
        return React.createElement(getReactClass(), props, childList.toArray());
    }

    @JsType
    public static class ContextTypes {
        @JsIgnore
        public <T> T get(String name) {
            return Reflection.get(this, name);
        }

        @JsIgnore
        public <T> void set(String name, T value) {
            Reflection.set(this, name, value);
        }
    }
}
