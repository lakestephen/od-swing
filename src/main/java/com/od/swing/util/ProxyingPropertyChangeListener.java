package com.od.swing.util;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
* Created by IntelliJ IDEA.
* User: Nick Ebbutt
* Date: 12/01/11
* Time: 16:59
*
* Can be used to forward on property change event from a associated object, as if they came from the owner source object
*/
public class ProxyingPropertyChangeListener implements PropertyChangeListener {

    private PropertyChangeSupport support;
    private Object source;
    private String propertyName;

    public ProxyingPropertyChangeListener(String propertyName, PropertyChangeSupport support, Object source) {
        this.propertyName = propertyName;
        this.support = support;
        this.source = source;
    }

    public ProxyingPropertyChangeListener(String propertyName) {
        this.propertyName = propertyName;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        support.firePropertyChange(propertyName, evt.getOldValue(), evt.getNewValue());
    }
}
