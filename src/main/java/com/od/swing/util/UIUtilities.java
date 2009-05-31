package com.od.swing.util;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Nick Ebbutt
 * Date: 24-May-2009
 * Time: 16:33:08
 */
public class UIUtilities {

    public static void runInDispatchThread(Runnable addToGui) {
        if (EventQueue.isDispatchThread()) {
            addToGui.run();
        } else {
            EventQueue.invokeLater(addToGui);
        }
    }
}
