package io.clickhandler.web.remoting;

/**
 *
 */
public class InOut<IN, OUT> {
    private final IN in;
    private final OUT out;

    public InOut(IN in, OUT out) {
        this.in = in;
        this.out = out;
    }

    public IN getIn() {
        return in;
    }

    public OUT getOut() {
        return out;
    }
}
