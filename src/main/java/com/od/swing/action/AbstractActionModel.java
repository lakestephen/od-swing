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
