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

import javax.swing.*;
import java.awt.*;
import java.util.Date;

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

    public static Window getWindowForComponentOrWindow(Component parent) {
        return parent instanceof Window ? (Window)parent : SwingUtilities.windowForComponent(parent);
    }

    /**
     * equals which checks for null
     */
    public static boolean equals(Object o1, Object o2) {
        return o1 == null ? o2 == null : o1.equals(o2);
    }
}
