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

/**
 * Copyright (c) Object Definitions Ltd.
 * <p/>
 * User: nick
 * Date: 30-Jul-2007
 * Time: 23:20:17
 */
public class WindowPosition
{
    public static void center(JFrame frame) 
    {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Point center = ge.getCenterPoint();
            Rectangle bounds = ge.getMaximumWindowBounds();
            int w = Math.max(bounds.width/2, Math.min(frame.getWidth(), bounds.width));
            int h = Math.max(bounds.height/2, Math.min(frame.getHeight(), bounds.height));
            int x = center.x - w/2, y = center.y - h/2;
            frame.setBounds(x, y, w, h);
            if (w == bounds.width && h == bounds.height)
                frame.setExtendedState(Frame.MAXIMIZED_BOTH);
            frame.validate();
    }

}
