package org.jcc.parsers;

import java.util.Collection;
import java.util.function.Consumer;

public class EventListenerUtility {

    private EventListenerUtility() {

    }

    public static <T> void fireEvent(T event, Collection<Consumer<T>> consumers) {
        consumers.forEach( c -> c.accept(event));
    }
}
