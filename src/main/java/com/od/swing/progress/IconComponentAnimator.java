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

import com.od.swing.util.ImageIconCache;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: ebbuttn
 * Date: 13-Feb-2008
 * Time: 14:30:05
 * To change this template use File | Settings | File Templates.
 *
 * Animate the icon for components which implement IconComponent
 *
 * Take care if using this to run continuous animations -
 * you will need to do some performance testing
 *
 * bug disclaimer...
 * this is heavily adapted version of the code at from http://www.swingwiki.org/howto:animated_label
 */
public class IconComponentAnimator implements AncestorListener {

    private ImageIcon[] icons;
    private int delayBetweenFrames;
    private int pauseBetween;
    private int currentIndex = 0;
    private boolean runOnce;
    private IconComponent iconComponent;
    private volatile AnimatorThread animatorThread;
    private volatile boolean isAnimationOn;
    private final Object animationStateLock = new Object();

    public IconComponentAnimator(IconComponent iconComponent, String imageResourcePrefix, String imageResourceSuffix,
                          int numImages, int startIndex, int delayBetweenFrames, int pauseBetweenAnimations, boolean runOnce) {
        this(iconComponent, imageResourcePrefix, imageResourceSuffix, numImages, startIndex, delayBetweenFrames, pauseBetweenAnimations, runOnce, -1, -1);
    }

    public IconComponentAnimator(IconComponent iconComponent, String imageResourcePrefix, String imageResourceSuffix,
                          int numImages, int startIndex, int delayBetweenFrames, int pauseBetweenAnimations, boolean runOnce, int iconWidth, int iconHeight) {

        icons = new ImageIcon[numImages];
        for (int i = 0; i < numImages; i++) {
            icons[i] = getOrCreateImageIcon(imageResourcePrefix, imageResourceSuffix, startIndex, i, iconWidth, iconHeight);
        }
        this.delayBetweenFrames = delayBetweenFrames;
        this.pauseBetween = pauseBetweenAnimations;
        this.runOnce = runOnce;
        setAnimatedComponent(iconComponent);
    }

    private ImageIcon getOrCreateImageIcon(String imageResourcePrefix, String imageResourceSuffix, int startIndex, int i, int width, int height) {
        return width < 0 || height < 0 ?
                ImageIconCache.getImageIcon(imageResourcePrefix + (i + startIndex) + imageResourceSuffix) :
                ImageIconCache.getImageIcon(imageResourcePrefix + (i + startIndex) + imageResourceSuffix, width, height);
    }

    private void setAnimatedComponent(IconComponent component) {
        iconComponent = component;
        iconComponent.setIcon(icons[0]);
        iconComponent.addAncestorListener(this);
    }

    /**
     * To promote garbage collection client class can call this dispose method, if paranoid
     */
    public void disposeResources() {
        Arrays.fill(icons, null);
        iconComponent.removeAncestorListener(this);
    }

    public void runAnimation() {
        synchronized(animationStateLock) {
            if ( ! isAnimationOn ) {
                isAnimationOn = true;
                animatorThread = new AnimatorThread();
                animatorThread.startAnimation();
            }
        }
    }

    public void stopAnimation() {
        synchronized(animationStateLock) {
            if ( isAnimationOn ) {
                isAnimationOn = false;
                animatorThread.stopAnimation();
            }
        }
    }

    public void joinThread() throws InterruptedException {
        animatorThread.join();
    }

    public void ancestorRemoved(AncestorEvent event) {
        stopAnimation();
    }

    //Why no AncestorAdapter?
    public void ancestorAdded(AncestorEvent event) {}
    public void ancestorMoved(AncestorEvent event) {}

    private class AnimatorThread extends Thread {

        private volatile boolean isAnimationThreadOn = false;

        public AnimatorThread() {
            setDaemon(true);
        }

        public void startAnimation() {
            isAnimationThreadOn = true;
            start();
        }

        public void stopAnimation() {
            isAnimationThreadOn = false;
            interrupt();
        }

        public void run() {
            try {
                while (isAnimationThreadOn) {
                    sleep(delayBetweenFrames);
                    currentIndex = (currentIndex + 1) % icons.length;
                    setIcon(icons[currentIndex]);
                    if (currentIndex == icons.length - 1 && runOnce)
                        isAnimationThreadOn = false;
                    if (currentIndex == 0)
                        sleep(pauseBetween);
                }

                //if run once only, we also need to set the main animation off
                //but only if this is still the current animator thread
                //Otherwise in the case there has been a quick call to stop and
                //restart we could be setting isAnimationOn to false while a new
                //thread is running and has taken over the animation
                synchronized(animationStateLock) {
                    if ( runOnce && animatorThread == this) {
                        isAnimationOn = false;
                    }
                }
                setIcon(icons[0]);

            }
            catch (InterruptedException ex) {

            }
            catch (InvocationTargetException ex) {
                ex.printStackTrace();
            }
        }

        private void setIcon(final Icon icon) throws InterruptedException, InvocationTargetException {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    iconComponent.setIcon(icon);
                }
            });
        }
    }

    /**
     * There is no convenient interface or superclass of JButton/JLabel with the setIcon method
     * Wrapping the component class as an IconComponent which delegates as appropriate
     * avoids all kinds of nasty if/else code tied to component type
     */
    public static interface IconComponent {
        void setIcon(Icon i);

        void addAncestorListener(AncestorListener l);

        void removeAncestorListener(AncestorListener l);
    }

}
