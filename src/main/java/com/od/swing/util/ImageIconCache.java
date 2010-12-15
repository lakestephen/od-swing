package com.od.swing.util;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

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

                ImageKey imageKey = new ImageKey(i.getIconWidth(), i.getIconHeight(), resourcePath);
                sizedImageMap.put(imageKey, i);
            }
        }
        return i;
    }

    /**
     * Call this from the UI event thread only
     * @return an ImageIcon loaded from resourcePath scaled to width, height
     */
    public static ImageIcon getImageIcon(String resource, int width, int height) {
        //use the temporary key for the lookup, rather than newing one up each time, to avoid object cycling
        ImageIcon i = sizedImageMap.get(ImageKey.getTemporaryKey(width, height, resource));
        if ( i == null ) {
            ImageIcon defaultSizedImage = getImageIcon(resource);
            if ( defaultSizedImage != null ) {
                Image scaled = defaultSizedImage.getImage().getScaledInstance(
                    width, height, java.awt.Image.SCALE_SMOOTH
                );
                i = new ImageIcon(scaled);
                sizedImageMap.put(new ImageKey(width, height, resource), i);
            }
        }
        return i;
    }

    private static class ImageKey {

        private static ImageKey temporaryKey = new ImageKey();

        int width, height;
        String resourceUrl;

        private ImageKey() {
        }

        private ImageKey(int width, int height, String resourceUrl) {
            this.width = width;
            this.height = height;
            this.resourceUrl = resourceUrl;
        }

        public static ImageKey getTemporaryKey(int width, int height, String resourceUrl) {
            temporaryKey.width = width;
            temporaryKey.height = height;
            temporaryKey.resourceUrl = resourceUrl;
            return temporaryKey;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ImageKey imageKey = (ImageKey) o;

            if (height != imageKey.height) return false;
            if (width != imageKey.width) return false;
            if (resourceUrl != null ? !resourceUrl.equals(imageKey.resourceUrl) : imageKey.resourceUrl != null)
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = width;
            result = 31 * result + height;
            result = 31 * result + (resourceUrl != null ? resourceUrl.hashCode() : 0);
            return result;
        }
    }
}
