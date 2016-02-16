package io.clickhandler.web.remoting;

import com.google.gwt.user.client.Timer;
import io.clickhandler.web.Bus;
import io.clickhandler.web.Func;
import io.clickhandler.web.JSON;
import io.clickhandler.web.Try;

import java.util.*;

/**
 * Remoting Dispatcher.
 * During the handshake with the other end, the capabilities
 * of each side is discovered and Wasabi will be the smallest
 * most efficient mode of transport.
 *
 * @author Clay Molocznik
 */
public class WsDispatcher {
    private final Bus bus;
    private final String url;
    private final Queue<Outgoing> pendingQueue = new LinkedList<>();
    private final LinkedHashMap<Integer, Outgoing> calls = new LinkedHashMap<>();

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

        // Did we get a response?
        if (envelope.isIn() != WsEnvelope.IN) {
            final Outgoing call = calls.remove(envelope.getId());
            if (call != null) {
                Try.silent(call.callback, envelope);
            }
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
    public void request(int timeoutMillis,
                        String type,
                        String payload,
                        Func.Run1<WsEnvelope> callback,
                        Func.Run timeoutCallback) {
        final WsEnvelope envelope = new WsEnvelope(WsEnvelope.IN, nextId(), 0, type, payload);
        final Outgoing call = new Outgoing(new Date().getTime(), timeoutMillis, envelope, callback, timeoutCallback);
        send(call);
    }

    /**
     *
     */
    private static final class Outgoing {
        private final long started;
        private final int timeoutMillis;
        private final WsEnvelope envelope;
        private final Func.Run1<WsEnvelope> callback;
        private final Func.Run timeoutCallback;
        private int tries = 0;

        public Outgoing(long started, int timeoutMillis, WsEnvelope envelope, Func.Run1<WsEnvelope> callback, Func.Run timeoutCallback) {
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
}
