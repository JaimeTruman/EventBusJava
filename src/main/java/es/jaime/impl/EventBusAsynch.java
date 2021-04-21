package es.jaime.impl;

import es.jaime.Event;
import es.jaime.EventBus;
import es.jaime.EventListenerInfo;
import es.jaime.EventsListenersMapper;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.var;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.Executor;

public final class EventBusAsynch implements Runnable, EventBus {
    private final Queue<Event> eventQueue;
    private final EventsListenersMapper eventsListenersMapper;

    public EventBusAsynch(Executor executor, String packageToScan) {
        this.eventsListenersMapper = new EventsListenersMapper(packageToScan);
        this.eventQueue = new PriorityQueue<>();

        executor.execute(this);
    }

    @Override
    public synchronized void publish (@NonNull Collection<? extends Event> messages) {
        messages.forEach(this::publish);
    }

    @Override
    public synchronized void publish (@NonNull Event message) {
        this.eventQueue.add(message);
    }

    @Override
    @SneakyThrows
    public void run() {
        while (true) {
            Event event = this.eventQueue.poll();

            if (event != null) {
                consumeEvent(event, eventsListenersMapper);
            }

            Thread.sleep(1);
        }
    }
}
