package io.clickhandler.web;

import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;
import io.clickhandler.web.action.EventCallback;

import java.util.ArrayList;

/**
 *
 */
public class BusDelegate {
    private final Bus delegate;

    private final ArrayList<HandlerRegistration> registrations = new ArrayList<>();

    public BusDelegate(Bus delegate) {
        this.delegate = delegate;
    }

    /**
     * @return
     */
    public EventBus getEventBus() {
        return delegate.getEventBus();
    }

    /**
     * @param eventClass
     * @param callback
     * @param <T>
     * @return
     */
    public <T> HandlerRegistration subscribe(Class<T> eventClass, EventCallback<T> callback) {
        return register(delegate.subscribe(eventClass, callback));
    }

    /**
     * @param named
     * @param callback
     * @param <T>
     * @return
     */
    public <T> HandlerRegistration subscribe(Bus.TypeName<T> named, EventCallback<T> callback) {
        return register(delegate.subscribe(named, callback));
    }

    /**
     * @param name
     * @param callback
     * @param <T>
     * @return
     */
    public <T> HandlerRegistration subscribe(String name, EventCallback<T> callback) {
        return register(delegate.subscribe(name, callback));
    }

    public HandlerRegistration register(HandlerRegistration registration) {
        if (registration == null) return null;
        HandlerRegistration r = new HandlerRegistration() {
            @Override
            public void removeHandler() {
                registrations.remove(this);
                registration.removeHandler();
            }
        };
        registrations.add(r);
        return r;
    }

    public void clear() {
        for (HandlerRegistration registration : registrations) {
            Try.silent(() -> registration.removeHandler());
        }
        registrations.clear();
    }

    /**
     * @param event
     * @param <T>
     */
    public <T> void publish(T event) {
        delegate.publish(event);
    }

    public <T> void publish(Bus.TypeName<T> name, T event) {
        delegate.publish(name, event);
    }

    public <T> void publish(String name, T event) {
        delegate.publish(name, event);
    }
}
