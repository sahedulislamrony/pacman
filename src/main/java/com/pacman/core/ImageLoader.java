package com.pacman.core;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ImageLoader {
    private static int instanceCount = 0;
    public ImageLoader(){
        instanceCount += 1;
    }
    public Image loadImage(String path) {
        URL resource = getClass().getClassLoader().getResource(path);
        if (resource == null) {
            System.err.println("ERROR: Resource not found -> " + path);
            return null;
        }
        ImageIcon icon = new ImageIcon(resource);
        if (icon.getIconWidth() == -1) {
            System.err.println("ERROR: Failed to load image -> " + path);
            return null;
        }

        return icon.getImage();
    }

    public Image scale(Image originalImage ,int scaledWidth ,int scaledHeight){
        return originalImage.getScaledInstance(scaledWidth,scaledHeight,Image.SCALE_SMOOTH);
    }
    public Image scale(Image originalImage,int scaleSize) {
        return originalImage.getScaledInstance(scaleSize,scaleSize,Image.SCALE_SMOOTH);
    }
}
