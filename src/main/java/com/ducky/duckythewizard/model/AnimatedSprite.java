package com.ducky.duckythewizard.model;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

public class AnimatedSprite extends Sprite
{
    public Image[] frames;
    public double duration;
//    private double positionX;
//    private double positionY;
//    private double velocityX;
//    private double velocityY;
//    private double width;
//    private double height;

    public Image getFrame(double time)
    {
//        System.out.println("time: " + time);
//        System.out.println("frames.length: " + frames.length);
//        System.out.println("duration: " + duration);
//        System.out.println((int)((time % (frames.length * duration)) / duration));
        int index = (int)((time % (frames.length * duration)) / duration);
        return frames[index];
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

//    public double getVelocityX() {
//        return velocityX;
//    }
//
//    public double getVelocityY() {
//        return velocityY;
//    }

//    public void setPosition(double x, double y)
//    {
//        positionX = x;
//        positionY = y;
//    }

//    public void setVelocity(double x, double y)
//    {
//        velocityX = x;
//        velocityY = y;
//    }

//    public void addVelocity(double x, double y)
//    {
//        velocityX += x;
//        velocityY += y;
//        //System.out.println("addVelocity, x: " + velocityX);
//    }

//    public void update(double time)
//    {
//        positionX += velocityX * time;
//        positionY += velocityY * time;
//    }

    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(positionX,positionY,frames[0].getWidth(),frames[0].getHeight());
    }

    public boolean intersects(int row, int column, double cellHeight, double cellWidth) {
        double xPosition = column * cellWidth;
        double yPosition = row * cellHeight;
        Rectangle2D imageBoundary = new Rectangle2D(xPosition,yPosition,cellWidth,cellHeight);

        //System.out.println("  X: " + i.getX() + ", Y: " + i.getY() + ", Width: " + i.getFitWidth() + ", Height: " + i.getFitHeight());
        return imageBoundary.intersects(this.getBoundary());
    }

    public boolean intersects(Sprite s)
    {
        return s.getBoundary().intersects( this.getBoundary() );
    }
}
