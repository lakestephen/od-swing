/**
 * Copyright (C) 2009 (nick @ objectdefinitions.com)
 *
 * This file is part of JTimeseries.
 *
 * JTimeseries is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JTimeseries is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JTimeseries.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.od.swing.action;

import com.od.swing.weakreferencelistener.WeakReferenceListener;

import javax.swing.*;

/**
 * @author EbbuttN
 */
public abstract class ModelDrivenAction<E extends ActionModel> extends AbstractAction implements ActionModelListener {

    private E actionModel;
    private WeakReferenceListener weakReferenceListener;

    public ModelDrivenAction(E actionModel, String name, ImageIcon imageIcon) {
        super(name, imageIcon);
        intialize(actionModel);
    }

    public ModelDrivenAction(E actionModel) {
        intialize(actionModel);
    }

    private void intialize(E actionModel) {
        this.actionModel = actionModel;
        weakReferenceListener = new WeakReferenceListener(this);
        weakReferenceListener.addListenerTo(actionModel);
        setEnabled(actionModel.isModelValid());
    }

    protected void finalize() throws Throwable {
        super.finalize();
        dispose();
    }

    public void dispose() {
        weakReferenceListener.removeListenerFrom(actionModel);
        actionModel = null;
    }

    public void actionStateUpdated() {
        setEnabled(actionModel.isModelValid() &&
                isPermitted() &&
                isModelStateActionable());
    }

    public E getActionModel() {
        return actionModel;
    }

    protected boolean isPermitted() {
        return true;
    }

    protected boolean isModelStateActionable() {
        return true;
    }
}
