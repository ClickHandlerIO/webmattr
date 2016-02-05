package webmattr.react;

import elemental.client.Browser;
import elemental.dom.Document;
import elemental.html.Console;
import elemental.html.Window;
import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import webmattr.Bus;
import webmattr.Func;
import webmattr.Reflection;
import webmattr.action.AbstractAction;
import webmattr.action.ActionCall;
import webmattr.action.ActionCalls;
import webmattr.action.ActionDispatcher;
import webmattr.dom.DOM;
import webmattr.router.History;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * @param <P>
 * @param <S>
 */
public abstract class Component<P, S> {

    protected final Console console = Browser.getWindow().getConsole();
    protected final Document document = Browser.getDocument();
    protected final Window window = Browser.getWindow();
    @JsProperty
    private final boolean __webmattr_component__$$__ = true;
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // Lifecycle
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @JsProperty
    private final Func.Run1<P> componentWillReceiveProps = Func.bind(this::componentWillReceiveProps0);
    @JsProperty
    private final Func.Run2<P, S> componentWillUpdate = Func.bind(this::componentWillUpdate0);
    @JsProperty
    private final Func.Run2<P, S> componentDidUpdate = Func.bind(this::componentDidUpdate0);
    @JsProperty
    private final Func.Run componentDidMount = Func.bind(this::componentDidMount0);
    @JsProperty
    private final Func.Run componentWillUnmount = Func.bind(this::componentWillUnmount0);
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // Defaults
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @JsProperty
    private String displayName = "";
    @JsProperty
    private ContextTypes contextTypes = new ContextTypes();
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // Props
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private Provider<P> propsProvider;
    @JsProperty
    private final Func.Call<P> getDefaultProps = Func.bind(this::getDefaultProps);
    private P propsType;
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // State
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private Provider<S> stateProvider;
    @JsProperty
    private final Func.Call2<Boolean, P, S> shouldComponentUpdate = Func.bind(this::shouldComponentUpdate0);
    @JsProperty
    private final Func.Call<S> getInitialState = Func.bind(this::getInitialState);
    private S stateType;
    @JsProperty
    private final Func.Run componentWillMount = Func.bind(this::componentWillMount0);
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // Render
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @JsProperty
    private final Func.Call<ReactElement> render = Func.bind(this::render0);
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private Object reactClass;
    private Bus bus;
    private History history;

    public Component() {
        addContextTypes(contextTypes);
        displayName = getDisplayName();
    }

    public static native boolean is(Object obj) /*-{
        return obj && obj['__webmattr_component__$$__'];
    }-*/;

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // Build Context Types Object.
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    protected void addContextTypes(ContextTypes contextTypes) {
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // Injected
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Inject
    void setPropsProvider(Provider<P> provider) {
        this.propsProvider = provider;
        propsType = provider.get();
    }

    @Inject
    void setStateProvider(Provider<S> provider) {
        this.stateProvider = provider;
        stateType = provider.get();
    }

    public Bus bus() {
        return bus;
    }

    @Inject
    void setBus(Bus bus) {
        this.bus = bus;
    }

    public History getHistory() {
        return history;
    }

    @Inject
    void setHistory(History history) {
        this.history = history;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // MyEventBus
    ///////////////////////////////////////////////////////////////////////////////////////////////////


    public String getDisplayName() {
        return getClass().getSimpleName();
    }

    private P getPropsType() {
        return propsType;
    }

    private S getStateType() {
        return stateType;
    }

    /**
     * @param $this
     * @return
     */
    public P getProps(ReactComponent $this) {
        return Reflection.copy(React.getProps($this), getPropsType());
    }

    /**
     * @param $this
     * @return
     */
    public S getState(ReactComponent $this) {
        return Reflection.copy(React.getState($this), getStateType());
    }

    /**
     * @param $this
     * @param state
     */
    public void setState(ReactComponent $this, S state) {
        $this.setState(state);
    }

    protected P getDefaultProps(ReactComponent<P, S> $this) {
        P props = props();
        initProps($this, props);
        return props;
    }

    /**
     * Invoked once when the component is mounted.
     * Values in the mapping will be set on this.props if that prop is not specified by the parent component (i.e. using an in check).
     * This method is invoked before onGetInitialState and therefore cannot rely on this.state or use this.setState.
     */
    @JsIgnore
    protected P initProps(ReactComponent<P, S> $this, P props) {
        return props;
    }

    @JsIgnore
    protected S getInitialState(ReactComponent<P, S> $this) {
        P props = getProps($this);
        return this.initState($this, props, stateProvider.get());
    }

    /**
     * @return
     */
    @JsIgnore
    protected S initState(ReactComponent<P, S> $this, P props, S state) {
        return state;
    }

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
    protected boolean shouldComponentUpdate0(final ReactComponent $this, Object nextProps, Object nextState) {
        return shouldComponentUpdate(
            $this,
            Reflection.copy(nextProps, propsProvider.get()),
            Reflection.copy(nextState, stateProvider.get())
        );
    }

    protected boolean shouldComponentUpdate(final ReactComponent $this, P nextProps, S nextState) {
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
        P props = getProps($this);
        S state = getState($this);
        ChildCounter.get().scope();
        try {
            return render($this, props, state);
        } finally {
            ChildCounter.get().pop();
        }
    }

    protected abstract ReactElement render(final ReactComponent<P, S> $this, P props, S state);

    @JsIgnore
    private void componentWillMount0(final ReactComponent $this) {
        if ($this != null) {
            // Register event handlers.
            Reflection.set($this, React.ACTION_CALLS, new ActionCalls());
            Reflection.set($this, React.ACTION, action());
            Reflection.set($this, React.GET_REF, (Func.Call1<Object, Ref>) ref -> ref.get($this));
            Reflection.set($this, React.GET_PROPS, (Func.Call<P>) () -> getProps($this));
            Reflection.set($this, React.GET_STATE, (Func.Call<S>) () -> getState($this));
            Reflection.set($this, React.GET_PROPERTY, (Func.Call1<Object, String>) name -> Reflection.get($this, name));
        }
        componentWillMount($this, getProps($this), getState($this));
    }

    private <H extends AbstractAction<IN, OUT>, IN, OUT> Func.Call1<ActionCall<IN, OUT>, Provider<H>> action() {
        return new Func.Call1<ActionCall<IN, OUT>, Provider<H>>() {
            @Override
            public ActionCall<IN, OUT> call(Provider<H> value) {
                return ActionDispatcher.action(value);
            }
        };
    }

    /**
     * @param $this
     * @param action
     * @param <H>
     * @param <IN>
     * @param <OUT>
     * @return
     */
    protected <H extends AbstractAction<IN, OUT>, IN, OUT> ActionCall<IN, OUT> dispatch(
        ReactComponent $this,
        Provider<H> action
    ) {
        ActionCalls calls = Reflection.get($this, React.ACTION_CALLS);
        if (calls == null) {
            calls = new ActionCalls();
            Reflection.set($this, React.ACTION_CALLS, calls);
        }

        final ActionCall<IN, OUT> call = ActionDispatcher.action(action);
        calls.add(call);
        return call;
    }

    /**
     * Invoked immediately before rendering occurs.``
     * If you call setState within this method, render() will see the updated state and will be executed only once despite the state change.
     */
    @JsIgnore
    protected void componentWillMount(final ReactComponent<P, S> $this, P props, S state) {

    }

    @JsIgnore
    private void componentDidMount0(final ReactComponent<P, S> $this) {
        if ($this != null) {
            Reflection.set($this, React.EVENT_BUS_REGISTRATIONS, new Object());
        }

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

    /**
     * Invoked when a component is receiving new props. This method is not called for the initial render.
     * <p/>
     * Use this as an opportunity to react to a prop transition before render() is called by updating the state using this.setState().
     * The old props can be accessed via this.props. Calling this.setState() within this function will not trigger an additional render.
     *
     * @param nextProps the props object that the component will receive
     */
    @JsIgnore
    protected void componentWillReceiveProps(final ReactComponent<P, S> $this, P nextProps) {
    }

    @JsIgnore
    private void componentWillUpdate0(final ReactComponent<P, S> $this, P nextProps, S nextState) {
        componentWillUpdate($this, nextProps, nextState);
    }

    /**
     * Invoked immediately before rendering when new props or state are being received. This method is not called for the initial render.
     * Use this as an opportunity to perform preparation before an update occurs.
     *
     * @param nextProps the props object that the component has received
     * @param nextState the state object that the component has received
     */
    @JsIgnore
    protected void componentWillUpdate(final ReactComponent<P, S> $this, P nextProps, S nextState) {
    }

    @JsIgnore
    private void componentDidUpdate0(final ReactComponent<P, S> $this, P nextProps, S nextState) {
        componentDidUpdate($this, nextProps, nextState);
    }

    /**
     * Invoked immediately after updating occurs. This method is not called for the initial render.
     * Use this as an opportunity to operate on the DOM when the component has been updated.
     *
     * @param nextProps the props object that the component has received
     * @param nextState the state object that the component has received
     */
    @JsIgnore
    protected void componentDidUpdate(final ReactComponent<P, S> $this, P nextProps, S nextState) {
    }

    @JsIgnore
    private void componentWillUnmount0(final ReactComponent<P, S> $this) {
        // Cleanup event registrations.
//        HandlerRegistration registrations = React.get($this, React.EVENT_BUS_REGISTRATIONS);
//        if (registrations != null) {
//            registrations.removeHandler();
//            React.delete($this, React.EVENT_BUS_REGISTRATIONS);
//        }

        // TODO: Cleanup.
//        React.set($this, React.ACTION_CALLS, new ActionCalls());
//        React.set($this, React.ACTION, action());
//        React.set($this, React.GET_REF, (Func.Call1<Object, Ref>) ref -> ref.get($this));
//        React.set($this, React.GET_PROPS, (Func.Call<P>) () -> getProps($this));
//        React.set($this, React.GET_STATE, (Func.Call<S>) () -> getState($this));
//        React.set($this, React.GET_PROPERTY, (Func.Call1<Object, String>) name -> React.get($this, name));

        componentWillUnmount($this);
    }

    /**
     * Invoked immediately before a component is unmounted from the DOM.
     * Perform any necessary cleanup in this method, such as invalidating timers or cleaning up any DOM elements that were created in componentDidMount.
     */
    @JsIgnore
    protected void componentWillUnmount(final ReactComponent<P, S> $this) {
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // Factory Methods
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    public Object getReactClass() {
        if (reactClass == null) {
            reactClass = React.createClass(this);
        }
        return reactClass;
    }

    protected P props() {
        // Create Props.
        final P props = propsProvider.get();
        try {
            // Init props.
            initProps(null, props);
        } catch (Throwable e) {
            // Ignore.
        }

        // Set key manually.
        final Object key = Reflection.get(props, "key");
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