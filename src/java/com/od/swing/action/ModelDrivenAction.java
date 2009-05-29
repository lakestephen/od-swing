package com.od.upcrypt.util.actionmodel;

import javax.swing.*;

/**
 * @author EbbuttN
 */
public abstract class ModelDrivenAction extends AbstractAction implements ActionModelListener {
    private AbstractActionModel actionModel;

    public ModelDrivenAction(AbstractActionModel actionModel, String name, ImageIcon imageIcon) {
        super(name, imageIcon);
        intialize(actionModel);
    }

    public ModelDrivenAction(AbstractActionModel actionModel) {
        intialize(actionModel);
    }

    private void intialize(AbstractActionModel actionModel) {
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

    protected boolean isPermitted() {
        return true;
    }

    protected boolean isModelStateActionable() {
        return true;
    }
}
