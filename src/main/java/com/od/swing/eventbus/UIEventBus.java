/**
 * Copyright (C) 2012 (nick @ objectdefinitions.com)
 *
 * This file is part of Object Definitions od-swing.
 *
 * od-swing is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * od-swing is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with od-swing.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.od.swing.eventbus;

import com.od.swing.util.UIUtilities;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Nick
 * Date: 12-Dec-2010
 * Time: 10:59:02
 */
public class UIEventBus {

    private static UIEventBus singleton;

    private Map<Class, List> listenerClassToListeners = new HashMap<Class, List>();

    private UIEventBus() {}

    public <E> boolean addEventListener(Class<E> listenerInterface, E listener) {
        List listeners = getListenerList(listenerInterface);

        boolean result = false;
        if (! listeners.contains(listener)) {
            listeners.add(listener);
            result = true;
        }
        return result;
    }

    public <E> boolean removeEventListener(Class<E> listenerInterface, E listener) {
        List listeners = getListenerList(listenerInterface);
        boolean result = false;
        //use a set to search, faster than list even with creation overhead
        return listeners.remove(listener);
    }

    public <E> void fireEvent(final Class<E> listenerClass, final EventSender<E> eventSender) {
        //run in event thread, if this is not already the event thread
        UIUtilities.runInDispatchThread(
            new Runnable() {
                public void run() {
                    List listeners = getListenerList(listenerClass);
                    LinkedList snapshot = new LinkedList(listeners);
                    for (Object o : snapshot) {
                        try {
                            eventSender.sendEvent((E) o);
                        } catch (Throwable t) {
                            t.printStackTrace();
                        }
                    }
                }
            }
        );
    }

    private <E> List getListenerList(Class<E> listenerInterface) {
        List listeners = listenerClassToListeners.get(listenerInterface);
        if ( listeners == null ) {
            listeners = new LinkedList<E>();
            listenerClassToListeners.put(listenerInterface, listeners);
        }
        return listeners;
    }

    public synchronized static UIEventBus getInstance() {
        if ( singleton == null) {
            singleton = new UIEventBus();
        }
        return singleton;
    }

}
