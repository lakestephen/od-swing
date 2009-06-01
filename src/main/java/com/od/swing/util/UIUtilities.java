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
