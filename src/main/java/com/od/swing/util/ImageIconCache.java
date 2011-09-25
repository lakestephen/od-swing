package com.od.swing.util;
import com.sun.jmx.snmp.tasks.ThreadService;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by IntelliJ IDEA.
 * User: Nick Ebbutt
 * Date: 11-Feb-2010
 * Time: 12:05:20
 *
 */
public class ImageIconCache {

    //static map to make sure we don't create duplicate image instances
    private static final Map<String, ImageIcon> resourceToImageMap = new HashMap<String, ImageIcon>();
    private static final Map<ImageKey, ImageIcon> sizedImageMap = new HashMap<ImageKey, ImageIcon>();

    /**
     * Call this from the UI event thread only
     * @return an ImageIcon loaded from resourcePath
     */
    public static ImageIcon getImageIcon(String resourcePath) {
        ImageIcon i = resourceToImageMap.get(resourcePath);
        if (i == null) {
            URL imageResource = ImageIconCache.class.getResource(resourcePath);
            if ( imageResource != null) {
                i = new ImageIcon(imageResource);
                resourceToImageMap.put(resourcePath, i);

                ImageKey imageKey = new ImageKey(i.getIconWidth(), i.getIconHeight(), resourcePath, 0);
                sizedImageMap.put(imageKey, i);
            }
        }
        return i;
    }

    public static ImageIcon getImageIcon(String resource, int width, int height) {
        return getImageIcon(resource, width, height, 0);
    }

    /**
     * Call this from the UI event thread only
     * @return an ImageIcon loaded from resourcePath scaled to width, height
     */
    public static ImageIcon getImageIcon(String resource, int width, int height, double rotation) {
        //use the temporary key for the lookup, rather than newing one up each time, to avoid object cycling
        ImageIcon i = sizedImageMap.get(ImageKey.getTemporaryKey(width, height, resource, rotation));

        if ( i == null ) {
            ImageIcon defaultSizedImage = getImageIcon(resource);
            if ( defaultSizedImage != null ) {
                BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
                Graphics2D g2d = (Graphics2D)bi.getGraphics();
                AffineTransform at = g2d.getTransform();
                double xScale = 1 / (((double) defaultSizedImage.getIconWidth()) / width);
                double yScale = 1 / (((double) defaultSizedImage.getIconHeight()) / height);
                //System.out.println("XScale: " + xScale + " YScale: " + yScale);
                at.rotate(rotation, width / 2, height / 2);
                at.scale(xScale, yScale);
                g2d.setTransform(at);
                g2d.drawImage(defaultSizedImage.getImage(), 0, 0, null);
                i = new ImageIcon(bi);
                sizedImageMap.put(new ImageKey(width, height, resource, rotation), i);
            }
        }
        return i;
    }

    private static class ImageKey {

        private static ImageKey temporaryKey = new ImageKey();

        int width, height;
        String resourceUrl;
        private double rotation;

        private ImageKey() {
        }

        private ImageKey(int width, int height, String resourceUrl, double rotation) {
            this.width = width;
            this.height = height;
            this.resourceUrl = resourceUrl;
            this.rotation = rotation;
        }

        public static ImageKey getTemporaryKey(int width, int height, String resourceUrl, double rotation) {
            temporaryKey.width = width;
            temporaryKey.height = height;
            temporaryKey.resourceUrl = resourceUrl;
            temporaryKey.rotation = rotation;
            return temporaryKey;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ImageKey imageKey = (ImageKey) o;

            if (height != imageKey.height) return false;
            if (Double.compare(imageKey.rotation, rotation) != 0) return false;
            if (width != imageKey.width) return false;
            if (resourceUrl != null ? !resourceUrl.equals(imageKey.resourceUrl) : imageKey.resourceUrl != null)
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            result = width;
            result = 31 * result + height;
            result = 31 * result + (resourceUrl != null ? resourceUrl.hashCode() : 0);
            temp = rotation != +0.0d ? Double.doubleToLongBits(rotation) : 0L;
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            return result;
        }
    }
}
