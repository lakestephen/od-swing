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
