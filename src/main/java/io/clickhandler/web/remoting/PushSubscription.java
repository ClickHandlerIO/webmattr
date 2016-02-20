package io.clickhandler.web.remoting;

import com.google.web.bindery.event.shared.HandlerRegistration;
import io.clickhandler.web.Bus;
import io.clickhandler.web.EventCallback;

/**
 *
 */
public class PushSubscription<T> implements HandlerRegistration {
    private final String name;
    private final String address;
    private final Bus.TypeName<T> typeName;
    private final Bus.TypeName<T> scopedTypeName;
    private final PushPublisher<T> publisher;
    private final EventCallback<T> callback;
    private final String id;
    HandlerRegistration dispatcherReg;
    private HandlerRegistration busReg;

    public PushSubscription(String name,
                            Bus.TypeName<T> typeName,
                            PushPublisher<T> publisher,
                            EventCallback<T> callback) {
        this(name, typeName, publisher, callback, null);
    }

    public PushSubscription(String name,
                            Bus.TypeName<T> typeName,
                            PushPublisher<T> publisher,
                            EventCallback<T> callback,
                            String id) {
        this.name = name;
        this.typeName = typeName;
        this.scopedTypeName = id != null ? Bus.scoped(typeName, id) : null;
        this.publisher = publisher;
        this.callback = callback;
        this.id = id;
        this.address = String.valueOf(name) + (id == null ? "" : "|" + id);
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Bus.TypeName<T> getTypeName() {
        return typeName;
    }

    public Bus.TypeName<T> getScopedTypeName() {
        return scopedTypeName;
    }

    public PushPublisher<T> getPublisher() {
        return publisher;
    }

    /**
     * @return
     */
    public String getAddress() {
        return address;
    }

    void subscribe(Bus bus) {
        if (busReg != null) return;
        busReg = bus.subscribe(scopedTypeName != null ? scopedTypeName : typeName, callback);
    }

    @Override
    public void removeHandler() {
        try {
            if (dispatcherReg != null)
                dispatcherReg.removeHandler();
            dispatcherReg = null;
        } finally {
            if (busReg != null)
                busReg.removeHandler();
            busReg = null;
        }
    }
}
