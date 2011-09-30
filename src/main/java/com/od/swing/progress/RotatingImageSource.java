package com.od.swing.progress;

import com.od.swing.util.ImageIconCache;

import javax.swing.*;
import java.util.List;

/**
* Created by IntelliJ IDEA.
* User: Nick Ebbutt
* Date: 25/09/11
* Time: 11:06
*/
public class RotatingImageSource extends AbstractImageFileSource {

    private String imageFileResource;
    private int width;
    private int height;
    private float alphaTransparency;

    public RotatingImageSource(String imageFileResource, int numImages, int width, int height) {
        this(imageFileResource, numImages, width, height, 1f);
    }

    public RotatingImageSource(String imageFileResource, int numImages, int width, int height, float alphaTransparency) {
        super(numImages);
        this.imageFileResource = imageFileResource;
        this.width = width;
        this.height = height;
        this.alphaTransparency = alphaTransparency;
    }

    protected void addImage(List<ImageIcon> icons, int imageId) {
        double rotationRadians = (((float) imageId) / numImages) * Math.PI * 2;
        ImageIcon i = ImageIconCache.getImageIcon(imageFileResource, width, height, rotationRadians, alphaTransparency);
        icons.add(i);
    }
}
