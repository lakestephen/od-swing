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
