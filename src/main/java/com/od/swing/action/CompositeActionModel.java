package com.od.swing.action;

import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nick Ebbutt
 * Date: 24/02/12
 * Time: 18:14
 */
public class CompositeActionModel extends AbstractActionModel {

    private List<ActionModel> delegateModels;

    public CompositeActionModel(ActionModel... models) {
        this(Arrays.asList(models));
    }

    public CompositeActionModel(List<ActionModel> delegateModels) {
        this.delegateModels = delegateModels;
        addDelegateListener();
        checkValidity();
    }

    private void addDelegateListener() {
        ActionModelListener delegateListener = new ActionModelListener() {
            public void actionStateUpdated() {
                checkValidity();
            }
        };

        for ( ActionModel m : delegateModels) {
            m.addActionModelListener(delegateListener);
        }
    }

    private void checkValidity() {
        //model is valid only if all delegate models are valid
        boolean result = true;
        for ( ActionModel m : delegateModels) {
            if ( ! m.isModelValid()) {
                result = false;
                break;
            }
        }

        boolean changed = setModelValid(result);
        if ( changed ) {
            fireActionStateUpdated();
        }
    }

    protected void doClearActionModelState() {
        for ( ActionModel m : delegateModels) {
            m.clearActionModelState();
        }
    }
}
