package com.od.swing.progress;

import javax.swing.*;

/**
 * Animate a component with an icon
 */
public class AnimatedButton extends JButton implements IconComponentAnimator.IconComponent {

    private IconComponentAnimator iconComponentAnimator;

    public AnimatedButton(Action action, String imageResourcePrefix, String imageResourceSuffix,
                          int numImages, int startIndex, int delay, int pauseBetweenAnimations, boolean runOnce) {
        super(action);

        iconComponentAnimator = new IconComponentAnimator(
                this,
                imageResourcePrefix,
                imageResourceSuffix,
                numImages,
                startIndex,
                delay,
                pauseBetweenAnimations,
                runOnce
        );
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
