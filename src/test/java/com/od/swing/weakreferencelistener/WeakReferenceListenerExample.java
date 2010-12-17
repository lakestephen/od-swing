package com.od.swing.weakreferencelistener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Created by IntelliJ IDEA.
 * User: Nick Ebbutt
 * Date: 15/12/10
 * Time: 15:59
 */
public class WeakReferenceListenerExample {

    private PropertyChangeSupport bean;
    private PropertyChangeListener propertyListener;
    private ActionListener actionListener;
    private ButtonModel buttonModel = new DefaultButtonModel();


    public WeakReferenceListenerExample() {

        propertyListener1();
        actionListener();

    }

    private void actionListener() {
        actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doSomeStuff();
            }
        };

        WeakReferenceListener l = new WeakReferenceListener(actionListener);
        l.addListenerTo(buttonModel);
    }

    private void doSomeStuff() {

    }

    private void propertyListener1() {
        propertyListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                updateUi();
            }
        };

        WeakReferenceListener l = new WeakReferenceListener(propertyListener);
        l.addListenerTo(bean);
    }

     private void propertyListener2() {
        propertyListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                updateUi();
            }
        };

        WeakReferenceListener l = new WeakReferenceListener("myPropertyName", propertyListener);
        l.addListenerTo(bean);
    }

    private void noAnonymousListener() {
        //DON'T DO THIS
        //your ui class must keep a reference to the listener you pass in to WeakReferenceListener, usually as a field.
        WeakReferenceListener l = new WeakReferenceListener("myPropertyName", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                updateUi();
            }
        });
        l.addListenerTo(bean);
    }

    private void updateUi() {


    }
}
