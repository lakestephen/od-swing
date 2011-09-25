package com.od.swing.progress;

import com.od.swing.util.ImageIconCache;

import javax.swing.*;
import java.util.List;

/**
 * Use when you have individual images for each frame in files named using consecutive index
 * e.g. myImage1.png, myImage2.png, myImage3.png
 * prefix = myImage, suffix = png, numImages = 3, startIndex = 1
 *
 * width and height may be specified to resize the images, if either is < 1 then the default image size will be used
 */
public class SuffixedImageFileSource extends AbstractImageFileSource {

    private String imageResourcePrefix;
    private String imageResourceSuffix;
    private int startIndex;
    private int width;
    private int height;

    public SuffixedImageFileSource(String imageResourcePrefix, String imageResourceSuffix, int numImages, int startIndex, int width, int height) {
        super(numImages);
        this.imageResourcePrefix = imageResourcePrefix;
        this.imageResourceSuffix = imageResourceSuffix;
        this.startIndex = startIndex;
        this.width = width;
        this.height = height;
    }

    @Override
    protected void addImage(List<ImageIcon> icons, int imageId) {
        ImageIcon i = width < 1 || height < 1 ?
            ImageIconCache.getImageIcon(imageResourcePrefix + (imageId + startIndex) + imageResourceSuffix) :
            ImageIconCache.getImageIcon(imageResourcePrefix + (imageId + startIndex) + imageResourceSuffix, width, height);
        icons.add(i);
    }
}
