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
