package io.clickhandler.web.action;

import io.clickhandler.web.Try;

import java.util.HashSet;

/**
 *
 */
public class ActionCalls {
    private HashSet<ActionCall> calls;

    public void add(ActionCall call) {
        if (calls == null) {
            calls = new HashSet<>();
        }
        calls.add(call);
    }

    public void clear() {
        if (calls == null || calls.isEmpty()) {
            calls = null;
            return;
        }

        for (ActionCall call : calls) {
            Try.silent(() -> call.cleanup(true));
        }
        calls.clear();
    }

    void remove(ActionCall call) {
        calls.remove(call);
        if (calls.isEmpty()) {
            calls = null;
        }
    }
}
