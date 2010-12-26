/**
 * Copyright (C) 2009 (nick @ objectdefinitions.com)
 *
 * This file is part of JTimeseries.
 *
 * JTimeseries is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JTimeseries is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JTimeseries.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.od.swing.progress;

import javax.swing.*;

/**
 * Animate a component with an icon
 */
public class AnimatedButton extends JButton implements IconComponentAnimator.IconComponent {

    private IconComponentAnimator iconComponentAnimator;

    public AnimatedButton(Action action, String imageResourcePrefix, String imageResourceSuffix,
            int numImages, int startIndex,
            int delay, int pauseBetweenAnimations, boolean runOnce) {
        this(action, imageResourcePrefix, imageResourceSuffix, numImages, startIndex, delay, pauseBetweenAnimations, runOnce, -1, -1);
    }

    public AnimatedButton(Action action, String imageResourcePrefix, String imageResourceSuffix,
                          int numImages, int startIndex, int delay, int pauseBetweenAnimations, boolean runOnce, int width, int height) {
        super(action);

        iconComponentAnimator = new IconComponentAnimator(
                this,
                imageResourcePrefix,
                imageResourceSuffix,
                numImages,
                startIndex,
                delay,
                pauseBetweenAnimations,
                runOnce,
                width,
                height
        );
    }

    public void setBackgroundImage(String resource, float alpha) {
        iconComponentAnimator.setBackgroundImage(resource, alpha);
    }

    /**
     * To guarantee cleanup client class can call this dispose method, if paranoid
     */
    public void disposeResources() {
        iconComponentAnimator.disposeResources();
    }

    public void runAnimations() {
        iconComponentAnimator.runAnimation();
    }

    public void stopAnimations() {
        iconComponentAnimator.stopAnimation();
    }

    public void joinAnimationThread() throws InterruptedException {
        iconComponentAnimator.joinThread();
    }

}
