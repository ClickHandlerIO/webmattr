package io.clickhandler.web.remoting;

import com.google.gwt.user.client.Timer;
import com.google.web.bindery.event.shared.HandlerRegistration;
import io.clickhandler.web.Bus;
import io.clickhandler.web.Func;
import io.clickhandler.web.JSON;
import io.clickhandler.web.Try;

import java.util.*;

/**
 * Remoting Dispatcher.
 *
 * @author Clay Molocznik
 */
public class WsDispatcher {
    private final static int DEFAULT_SUB_TIMEOUT = 5_000;
    private final Bus bus;
    private final String url;
    private final Queue<Outgoing> pendingQueue = new LinkedList<>();
    private final LinkedHashMap<Integer, Outgoing> calls = new LinkedHashMap<>();
    private final Map<String, Subscription> subMap = new HashMap<>();
    private Ws webSocket;
    private int reaperMillis = 500;
    private Timer reaperTimer;
    private int id = 0;

    public WsDispatcher(Bus bus, String url) {
        this.bus = bus;
        this.url = url;
    }

    public Bus getBus() {
        return bus;
    }

    public String getUrl() {
        return url;
    }

    public Ws getWebSocket() {
        return webSocket;
    }

    @SuppressWarnings("unchecked")
    public <T> HandlerRegistration subscribe(PushAddress<T> address, Func.Run1<T> callback) {
        if (address == null || callback == null)
            return null;

        final String addr = address.getAddress();
        if (addr == null || addr.isEmpty()) {
            return null;
        }

        Subscription<T> registration = subMap.get(addr);

        if (registration == null) {
            registration = new Subscription<>(addr, address.getTypeName());
            subMap.put(addr, registration);
        }

        registration.subscribe(callback);
        return registration;
    }

    /**
     * @return
     */
    private boolean isEmpty() {
        return calls.isEmpty() && pendingQueue.isEmpty();
    }

    /**
     *
     */
    public void start() {
        if (webSocket != null) {
            return;
        }

        webSocket = new Ws(bus, url, this::connected, this::closed, this::error, this::data);
        webSocket.connect();
    }

    /**
     *
     */
    private void drainQueue() {
        if (!webSocket.isConnected()) {
            return;
        }

        int count = 0;
        while (!pendingQueue.isEmpty()) {
            if (count > 10) {
                break;
            }
            send(pendingQueue.remove());
            count++;
        }

        // Give a little break.
        if (!pendingQueue.isEmpty()) {
            Try.later(0, this::drainQueue);
        }
    }

    /**
     *
     */
    private void connected() {
        // Try draining the queue.
        drainQueue();
    }

    /**
     *
     */
    private void closed() {
        // Is this ok?
    }

    /**
     *
     */
    private void error() {
        // Yikes!!!
    }

    /**
     * @param payload
     */
    private void data(String payload) {
        // Parse envelope.
        final WsEnvelope envelope = JSON.parse(payload, new WsEnvelope());

        if (envelope == null) {
            return;
        }

        // Publish a new WsEnvelopeEvent to the Bus.
        bus.publish(new WsEnvelopeEvent(this, envelope));

        switch (envelope.isIn()) {
            case WsEnvelope.IN:
                // Incoming request.
                break;
            case WsEnvelope.OUT:
                // It's a response.
                final Outgoing call = calls.remove(envelope.getId());
                if (call != null) {
                    Try.silent(call.callback, envelope);
                }
                break;
            case WsEnvelope.PUSH:
                // It's a push event.
                // Fire on main event bus.
                String type = envelope.getType();
                if (type == null) type = "";
                else type = type.trim();

                String body = envelope.getPayload();
                if (body == null) body = "";
                else body = body.trim();

                if (body.isEmpty()) body = "{}";

                final Subscription subscription = subMap.get(type);
                if (subscription != null) {
                    subscription.dispatch(JSON.parse(body));
                }

                break;
        }
    }

    /**
     * @param call
     */
    private void send(Outgoing call) {
        call.tries++;
        if (!webSocket.isConnected()) {
            pendingQueue.add(call);
        } else {
            try {
                calls.put(call.envelope.getId(), call);

                try {
                    webSocket.send(JSON.stringify(call.envelope));
                } catch (Throwable e) {
                    pendingQueue.add(call);
                }
            } finally {
                ensureReaper();
            }
        }
    }

    /**
     * @return
     */
    private int nextId() {
        id++;
        if (id < 0) {
            id = 1;
        }
        return id;
    }

    /**
     *
     */
    private void ensureReaper() {
        if (calls.isEmpty()) {
            stopReaper();
            return;
        }
        if (reaperTimer != null) {
            return;
        }
        reaperTimer = new Timer() {
            @Override
            public void run() {
                Try.silent(() -> reap());
            }
        };
        reaperTimer.scheduleRepeating(reaperMillis);
    }

    /**
     *
     */
    private void stopReaper() {
        if (reaperTimer != null) {
            try {
                reaperTimer.cancel();
            } finally {
                reaperTimer = null;
            }
        }
    }

    /**
     *
     */
    private void reap() {
        if (isEmpty()) {
            stopReaper();
            return;
        }

        if (!calls.isEmpty()) {
            reap(calls.values().iterator());
        }

        if (!pendingQueue.isEmpty()) {
            reap(pendingQueue.iterator());
        }
    }

    /**
     * @param iterator
     */
    private void reap(Iterator<Outgoing> iterator) {
        final long time = new Date().getTime();
        List<Outgoing> timeoutList = null;

        // Check current calls.
        while (iterator.hasNext()) {
            final Outgoing outgoing = iterator.next();

            if (outgoing.isTimedOut(time)) {
                if (timeoutList == null) {
                    timeoutList = new LinkedList<>();
                }
                timeoutList.add(outgoing);
                iterator.remove();
            }
        }

        if (timeoutList != null && !timeoutList.isEmpty()) {
            for (Outgoing call : timeoutList) {
                Try.silent(call.timeoutCallback);
            }
        }
    }


    /**
     * @param timeoutMillis
     * @param type
     * @param payload
     * @param callback
     * @param timeoutCallback
     */
    public void request(Bus.TypeName inType,
                        Bus.TypeName outType,
                        int timeoutMillis,
                        String type,
                        String payload,
                        Func.Run1<WsEnvelope> callback,
                        Func.Run timeoutCallback) {
        final WsEnvelope envelope = new WsEnvelope(WsEnvelope.IN, nextId(), 0, type, payload);
        final Outgoing call = new Outgoing(inType, outType, new Date().getTime(), timeoutMillis, envelope, callback, timeoutCallback);
        send(call);
    }

    public enum SubState {
        INVALID,
        NOT_REGISTERED,
        REGISTERING,
        REGISTERED,;
    }

    /**
     *
     */
    private static final class Outgoing {
        private final Bus.TypeName inType;
        private final Bus.TypeName outType;
        private final long started;
        private final int timeoutMillis;
        private final WsEnvelope envelope;
        private final Func.Run1<WsEnvelope> callback;
        private final Func.Run timeoutCallback;
        private int tries = 0;

        public Outgoing(Bus.TypeName inType,
                        Bus.TypeName outType,
                        long started,
                        int timeoutMillis,
                        WsEnvelope envelope,
                        Func.Run1<WsEnvelope> callback,
                        Func.Run timeoutCallback) {
            this.inType = inType;
            this.outType = outType;
            this.started = started;
            this.timeoutMillis = timeoutMillis;
            this.envelope = envelope;
            this.callback = callback;
            this.timeoutCallback = timeoutCallback;
        }

        public boolean isTimedOut(long time) {
            return (time - started) > timeoutMillis;
        }
    }

    /**
     * Manages the lifecycle of a single subscription to a single address.
     * Multiple local listeners may be registered.
     * It automatically subscribes with the server upon the first local listener and un-subscribes when
     * the last local listener is removed.
     *
     * @param <T>
     */
    private class Subscription<T> implements HandlerRegistration {
        private final String name;
        private final Bus.TypeName<T> typeName;
        private SubState state = SubState.NOT_REGISTERED;
        private ArrayList<LocalSub> subs = new ArrayList<>();

        public Subscription(String name, Bus.TypeName<T> typeName) {
            this.name = name;
            this.typeName = typeName;
            subscribe();
        }

        /**
         *
         */
        @Override
        public void removeHandler() {
            final Subscription subscription = subMap.remove(name);
            if (subscription == null) {
                return;
            }

            for (LocalSub sub : subs) {
                sub.removeHandler();
            }

            if (state == SubState.INVALID)
                return;

            send(
                new Outgoing(
                    null,
                    null,
                    new Date().getTime(),
                    DEFAULT_SUB_TIMEOUT,
                    new WsEnvelope(WsEnvelope.USUB, nextId(), 0, name, null),
                    envelope -> setState(envelope.getCode() == 200 ? SubState.REGISTERED : SubState.NOT_REGISTERED),
                    () -> setState(SubState.NOT_REGISTERED)
                )
            );
        }

        /**
         * @param state
         */
        public void setState(SubState state) {
            this.state = state;
            if (state == SubState.NOT_REGISTERED) {
                subscribe();
            }
        }

        /**
         *
         */
        public void subscribe() {
            if (state == SubState.REGISTERED || state == SubState.REGISTERING) {
                return;
            }
            setState(SubState.REGISTERING);
            send(
                new Outgoing(
                    null,
                    null,
                    new Date().getTime(),
                    DEFAULT_SUB_TIMEOUT,
                    new WsEnvelope(WsEnvelope.SUB, nextId(), 0, name, null),
                    envelope -> {
                        // Was it a valid subscription.
                        if (envelope.getCode() == 404) {
                            removeHandler();
                            return;
                        }
                        setState(envelope.getCode() == 200 ? SubState.REGISTERED : SubState.NOT_REGISTERED);
                    },
                    () -> setState(SubState.NOT_REGISTERED)
                )
            );
        }

        /**
         * @param event
         */
        public void dispatch(T event) {
            bus.publish(typeName, event);

            Try.later(() -> {
                for (LocalSub sub : subs) {
                    sub.callback.run(event);
                }
            });
        }

        /**
         * @param callback
         * @return
         */
        public HandlerRegistration subscribe(Func.Run1<T> callback) {
            final LocalSub sub = new LocalSub(callback);
            subs.add(sub);
            return sub;
        }

        /**
         * @param sub
         */
        private void unsubscribe(LocalSub sub) {
            subs.remove(sub);

            if (subs.isEmpty()) {
                removeHandler();
            }
        }

        /**
         *
         */
        private class LocalSub implements HandlerRegistration {
            private final Func.Run1<T> callback;

            public LocalSub(Func.Run1<T> callback) {
                this.callback = callback;
            }

            @Override
            public void removeHandler() {
                unsubscribe(this);
            }
        }
    }
}
