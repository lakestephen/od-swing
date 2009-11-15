/**
 * Copyright (C) 2009 (nick @ objectdefinitions.com)
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
package com.od.swing.progress;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Nick Ebbutt
 * Date: 09-Apr-2009
 * Time: 10:00:04
 */
public class ProgressUtilities {

    public static final ProgressIndicator DUMMY_PROGRESS_INDICATOR = new ProgressIndicator() {

        public void startProgressAnimation(String message) {}

        public void stopProgressAnimation() {}

        public void setProgress(boolean displayProgressBar, int currentStep, int totalSteps, String message) {}
    };

    /**
     * @return  The component itself, if it implements ProgressIndicator.
     * Alternatively returns the first available ancestor in the component hierarchy which implements
     * ProgressIndicator, or the DUMMY_PROGRESS_INDICATOR if there is no such component available
     */
    public static ProgressIndicator findProgressIndicator(Component c) {
        ProgressIndicator result;
        if ( c instanceof ProgressIndicator) {
            result = (ProgressIndicator)c;
        } else if ( c.getParent() != null ) {
            result = findProgressIndicator(c.getParent());
        } else {
            result = DUMMY_PROGRESS_INDICATOR;
        }
        return result;
    }
}
