package com.od.swing.progress;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: Nick
 * Date: 26/12/10
 * Time: 11:59
 * To change this template use File | Settings | File Templates.
 */
public class AnimatedLabelTestHarness {

    private static final int IMAGE_SIZE = 32;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        JFrame f = new JFrame();

                        SuffixedImageFileSource i = new SuffixedImageFileSource(
                            "/progressAnimation/loading",
                            ".gif",
                            18,
                            1,
                            IMAGE_SIZE,
                            IMAGE_SIZE
                        );

//                        RotatingImageSource i = new RotatingImageSource(
//                            "/progressAnimation/loading1.gif",
//                            18,
//                            IMAGE_SIZE,
//                            IMAGE_SIZE
//                        );

                        AnimatedLabel l = new AnimatedLabel(
                            i, 200, 0, false
                        );
                        l.setBackgroundImage("/hard_drive_network_warning.png", 0.75f);
                        l.runAnimations();

                        f.getContentPane().add(l);
                        f.pack();
                        f.setVisible(true);
                    }
                }
        );

    }
}
