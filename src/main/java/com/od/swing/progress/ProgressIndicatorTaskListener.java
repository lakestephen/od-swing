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
package com.od.swing.progress;

import com.od.swing.progress.ProgressIndicator;
import com.od.swing.progress.ProgressUtilities;
import swingcommand.Task;
import swingcommand.TaskListenerAdapter;

import java.awt.*;

/**
* Created by IntelliJ IDEA.
* User: nick
* Date: 15-Dec-2010
* Time: 17:39:28
* To change this template use File | Settings | File Templates.
*/
public class ProgressIndicatorTaskListener extends TaskListenerAdapter<String> {
    private ProgressIndicator i;
    private String progressText;
    private Component progressComponent;

    public ProgressIndicatorTaskListener(String progressText, Component progressComponent) {
        this.progressText = progressText;
        this.progressComponent = progressComponent;
    }

    public void pending(Task task) {
        i = ProgressUtilities.findProgressIndicator(progressComponent);
        if ( i != null) {
            i.startProgressAnimation(progressText);
        }
    }

    public void finished(Task task) {
        if ( i != null) {
            i.stopProgressAnimation();
        }
    }
}
