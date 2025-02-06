package com.pacman.core;


import java.awt.Image;

public class Block {
    private int x;
    private int y;
    private final int width;
    private final int height;
    private Image image;

    private final int startX;
    private final int startY;
    private char direction = 'U'; // U D L R
    private int velocityX = 0;
    private int velocityY = 0;

    public Block(Image image, int x, int y, int width, int height) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.startX = x;
        this.startY = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Image getImage() { return image; }
    public char getDirection() { return direction; }
    public int getVelocityX() { return velocityX; }
    public int getVelocityY() { return velocityY; }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setImage(Image image) { this.image = image; }
    public void setDirection(char direction) { this.direction = direction; }
    public void setVelocityX(int velocityX) { this.velocityX = velocityX; }
    public void setVelocityY(int velocityY) { this.velocityY = velocityY; }

    public void reset() {
        this.x = this.startX;
        this.y = this.startY;
    }
}