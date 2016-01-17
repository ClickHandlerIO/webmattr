package webmattr.react;

/**
 *
 */
public abstract class StatelessComponent<P> extends Component<P, Object> {
    @Override
    protected ReactElement render(ReactComponent<P, Object> $this, P props, Object state) {
        return render($this, props);
    }

    protected abstract ReactElement render(ReactComponent<P, Object> $this, P props);
}
