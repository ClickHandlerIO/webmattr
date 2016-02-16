package io.clickhandler.web.action;

import javax.inject.Provider;
import java.util.HashSet;

/**
 *
 */
public class ActionDispatcher {
    private static final ActionDispatcher instance = new ActionDispatcher();

    private static final int DEFAULT_TIMEOUT = 10000;

    private final HashSet<AbstractAction> actions = new HashSet<>();

    public static <H extends AbstractAction<IN, OUT>, IN, OUT> ActionCall<IN, OUT> action(Provider<H> action) {
        return action.get().build();
    }

    public void add() {

    }
}
