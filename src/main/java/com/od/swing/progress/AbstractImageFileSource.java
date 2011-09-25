package com.od.swing.progress;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
* Created by IntelliJ IDEA.
* User: Nick Ebbutt
* Date: 25/09/11
* Time: 11:06
*/
public abstract class AbstractImageFileSource implements ImageIconSource {

    protected int numImages;

    public AbstractImageFileSource(int numImages) {
        this.numImages = numImages;
    }

    public List<ImageIcon> getImageIcons() {
        List<ImageIcon> icons = new ArrayList<ImageIcon>();
        for (int imageId = 0; imageId < numImages; imageId++) {
            addImage(icons, imageId);
        }
        return icons;
    }

    protected abstract void addImage(List<ImageIcon> icons, int imageId);
}
