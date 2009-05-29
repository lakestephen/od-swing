package com.od.swing.action;

import javax.swing.*;

/**
 * @author EbbuttN
 */
public abstract class ModelDrivenAction<E extends AbstractActionModel> extends AbstractAction implements ActionModelListener {

    private E actionModel;

    public ModelDrivenAction(E actionModel, String name, ImageIcon imageIcon) {
        super(name, imageIcon);
        intialize(actionModel);
    }

    public ModelDrivenAction(E actionModel) {
        intialize(actionModel);
    }

    private void intialize(E actionModel) {
        this.actionModel = actionModel;
        actionModel.addActionModelListener(this);
        setEnabled(false);
    }

    protected void finalize() throws Throwable {
        super.finalize();
        dispose();
    }

    public void dispose() {
        actionModel.removeActionModelListener(this);
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
