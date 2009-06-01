/**
 * Copyright (C) 2009 Nick Ebbutt (nick@objectdefinitions.com)
 *
 * This file is part of JTimeseries.
 *
 * JTimeseries is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JTimeseries is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTimeseries.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.od.swing.action;

import java.util.ArrayList;
import java.util.List;

/**
 * @author EbbuttN
 *         <p/>
 *         Superclass for action models
 */
public abstract class AbstractActionModel {

    private boolean modelIsValid;

    private List<ActionModelListener> actionModelListeners = new ArrayList<ActionModelListener>();

    public synchronized void addActionModelListener(ActionModelListener a) {
        if (!actionModelListeners.contains(a)) {
            actionModelListeners.add(a);
        }
    }

    public synchronized void removeActionModelListener(ActionModelListener a) {
        actionModelListeners.remove(a);
    }

    public synchronized void fireActionStateUpdated() {
        for (ActionModelListener a : actionModelListeners) {
            if (a != null) {
                a.actionStateUpdated();
            }
        }
    }

    /**
     * Clear the state associated with this ActionModel and invalidate it
     */
    public synchronized void clearActionModelState() {
        doClearActionModelState();
        setModelValid(false);
    }

    /**
     * Set whether actions based on this model should be enabled
     * Triggers an event to the Actions which are listners on this model
     *
     * @param valid
     */
    protected synchronized void setModelValid(boolean valid) {
        modelIsValid = valid;
        fireActionStateUpdated();
    }

    /**
     * @return true, if model is in a state where actions should be able to use (i.e. actions based on this model can be enabled)
     */
    public synchronized boolean isModelValid() {
        return modelIsValid;
    }

    protected abstract void doClearActionModelState();


}
