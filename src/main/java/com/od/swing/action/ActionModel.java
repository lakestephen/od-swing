package com.od.swing.action;

/**
 * Created by IntelliJ IDEA.
 * User: Nick Ebbutt
 * Date: 24/02/12
 * Time: 18:15
 */
public interface ActionModel {

    void addActionModelListener(ActionModelListener a);

    void removeActionModelListener(ActionModelListener a);

    boolean isModelValid();

    void clearActionModelState();
}
