package webmattr.action;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class ActionCalls {
    private Map<Object, ActionCall> openCalls;

    public void add(ActionCall call) {
        if (openCalls == null) {
            openCalls = new HashMap<>();
        }
        ActionCall.Ref ref = () -> remove(call);
        openCalls.put(ref, call);
    }

    private void remove(ActionCall call) {
        openCalls.remove(call);
        if (openCalls.isEmpty()) {
            openCalls = null;
        }
    }
}
