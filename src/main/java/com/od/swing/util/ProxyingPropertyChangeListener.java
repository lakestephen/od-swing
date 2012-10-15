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

    public ProxyingPropertyChangeListener(PropertyChangeSupport support) {
        this.support = support;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        support.firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
    }
}
