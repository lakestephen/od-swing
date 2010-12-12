package com.od.swing.eventbus;

/**
 * Created by IntelliJ IDEA.
 * User: Nick
 * Date: 12-Dec-2010
 * Time: 16:03:51
 *
 * Supplied to the UIEventBus when an event is fired,
 * contains the logic to fire an event to a single listener
 */
public interface EventSender<E> {

    /**
     * @param listener, listener which should have an event called
     */
    void sendEvent(E listener);
}
