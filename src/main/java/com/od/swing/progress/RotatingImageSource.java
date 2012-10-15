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
