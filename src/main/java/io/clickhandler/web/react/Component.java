package io.clickhandler.web.react;

import elemental.client.Browser;
import elemental.dom.Document;
import elemental.html.Console;
import elemental.html.Window;
import io.clickhandler.web.*;
import io.clickhandler.web.dom.DOM;
import io.clickhandler.web.router.History;
import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

import javax.inject.Inject;

/**
 * @param <P>
 * @param <S>
 */
public abstract class Component<P, S> {
    protected final Console console = Browser.getWindow().getConsole();
    protected final Document document = Browser.getDocument();
    protected final Window window = Browser.getWindow();
    @Inject
    Bus bus;
    @JsProperty
    private final Func.Run componentWillMount = Func.bind(this::componentWillMount0);
    @Inject
    History history;
    @JsProperty
    private Func.Run2<P, S> componentDidUpdate = Func.bind(this::componentDidUpdate0);
    @JsProperty
    private Func.Run componentDidMount = Func.bind(this::componentDidMount0);
    @JsProperty
    private Func.Run componentWillUnmount = Func.bind(this::componentWillUnmount0);
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // Defaults
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @JsProperty
    private String displayName = "";
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // Lifecycle
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @JsProperty
    private Func.Run1<P> componentWillReceiveProps = Func.bind(this::componentWillReceiveProps0);
    @JsProperty
    private Func.Call<P> getDefaultProps = Func.bind(this::getDefaultProps);
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // State
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @JsProperty
    private Func.Run2<P, S> componentWillUpdate = Func.bind(this::componentWillUpdate0);
    @JsProperty
    private Func.Call<S> getInitialState = Func.bind(this::getInitialState);
    @JsProperty
    private Func.Call2<Boolean, P, S> shouldComponentUpdate = Func.bind(this::shouldComponentUpdate0);
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // Render
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @JsProperty
    private Func.Call<ReactElement> render = Func.bind(this::render0);
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private Object reactClass;
    @JsProperty
    private ContextTypes contextTypes = new ContextTypes();

    public Component() {
        displayName = getDisplayName();
        addContextTypes(contextTypes);
    }

    public static <T> T create() {
        return Jso.create();
    }

    public static <T> T create(Class<T> cls) {
        return Jso.create();
    }

    public static <T> T create(Class<T> cls, Func.Run1<T> callback) {
        return Jso.create(cls, callback);
    }

    protected String getDisplayName() {
        return getClass().getSimpleName();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // Build Context Types Object.
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    protected void addContextTypes(ContextTypes contextTypes) {
    }

    public P getDefaultProps(ReactComponent<P, S> $this) {
        return createProps();
    }

    public S getInitialState(ReactComponent<P, S> $this) {
        return createState();
    }

    protected native P createProps() /*-{
        return {};
    }-*/;

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
    @JsIgnore
    protected boolean shouldComponentUpdate0(final ReactComponent<P, S> $this, P nextProps, S nextState) {
        return shouldComponentUpdate($this, nextProps, nextState);
    }

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

    protected abstract ReactElement render(final ReactComponent<P, S> $this, P props, S state);

    @JsIgnore
    private void componentWillMount0(final ReactComponent<P, S> $this) {
        Reflection.set($this, React.BUS, new BusDelegate(bus));
        componentWillMount($this, $this.getProps(), $this.getState());
    }

    @JsIgnore
    protected void componentWillMount(final ReactComponent<P, S> $this, P props, S state) {

    }

    @JsIgnore
    private void componentDidMount0(final ReactComponent<P, S> $this) {
        componentDidMount($this);
    }

    /**
     * Invoked immediately after rendering occurs.
     * At this point in the lifecycle, the component has a DOM representation which you can access via the rootNode argument or by calling this.getDOMNode().
     * If you want to integrate with other JavaScript frameworks, set timers using setTimeout or setInterval,
     * or send AJAX requests, perform those operations in this method.
     */
    @JsIgnore
    protected void componentDidMount(final ReactComponent<P, S> $this) {
    }

    @JsIgnore
    private void componentWillReceiveProps0(final ReactComponent<P, S> $this, P nextProps) {
        componentWillReceiveProps($this, nextProps);
    }

    @JsIgnore
    protected void componentWillReceiveProps(final ReactComponent<P, S> $this, P nextProps) {

    }

    @JsIgnore
    protected void componentWillUpdate0(final ReactComponent<P, S> $this, P nextProps, S nextState) {

    }

    @JsIgnore
    private void componentDidUpdate0(final ReactComponent<P, S> $this, P nextProps, S nextState) {
        componentDidUpdate($this, nextProps, nextState);
    }

    protected void componentDidUpdate(final ReactComponent<P, S> $this, P nextProps, S nextState) {
    }

    @JsIgnore
    protected void componentWillUnmount0(final ReactComponent<P, S> $this) {
        try {
            $this.cleanup();
        } finally {
            componentWillUnmount($this);
        }
    }

    protected void componentWillUnmount(final ReactComponent<P, S> $this) {
    }

    @JsIgnore
    public ReactClass getReactClass() {
        if (reactClass == null) {
            reactClass = React.createClass(this);
        }
        return (ReactClass) reactClass;
    }

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
    public ReactElement $(Object... children) {
        return React.createElement(getReactClass(), props(), children);
    }

    /**
     * @return
     */
    public ReactElement $() {
        return React.createElement(getReactClass(), props());
    }

    /**
     * @param props
     * @return
     */
    public ReactElement $(P props) {
        return React.createElement(getReactClass(), props);
    }

    /**
     * @param props
     * @param childCallback
     * @return
     */
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
