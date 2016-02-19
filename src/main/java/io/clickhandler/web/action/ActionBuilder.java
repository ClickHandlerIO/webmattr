package io.clickhandler.web.action;

import javax.inject.Provider;

/**
 *
 */
public class ActionBuilder {
    public static <H extends AbstractAction<IN, OUT>, IN, OUT> ActionCall<IN, OUT> action(Provider<H> action) {
        return action.get().build();
    }
}
