package com.ducky.duckythewizard.model;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite
{
    protected Image image;
    protected double positionX;
    protected double positionY;
    protected double velocityX;
    protected double velocityY;
    protected double width;
    protected double height;

    public Sprite()
    {
        positionX = 0;
        positionY = 0;
        velocityX = 0;
        velocityY = 0;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setImage(Image i)
    {
        image = i;
        width = i.getWidth();
        height = i.getHeight();
    }

    public void setImage(String filename)
    {
        Image i = new Image(filename);
        setImage(i);
    }

    public void setPosition(double x, double y)
    {
        positionX = x;
        positionY = y;
    }

    public void setVelocity(double x, double y)
    {
//        System.out.println("setVelocity");
//        System.out.println("VORHER => x = " + velocityX + ", y = " + velocityY);
        velocityX = x;
        velocityY = y;
//        System.out.println("NACHHER => x = " + velocityX + ", y = " + velocityY);
    }

    public void setVelocityX(double x) {
        this.velocityX = x;
    }
    public void setVelocityY(double y) {
        velocityY = y;
    }

    public void addVelocity(double x, double y)
    {
//        System.out.println("addVelocity");
//        System.out.println("VORHER => x = " + velocityX + ", y = " + velocityY);
        velocityX += x;
        velocityY += y;
//        System.out.println("NACHHER => x = " + velocityX + ", y = " + velocityY);
    }

    public void invertVelocity() {
        //System.out.println("VORHER => x = " + velocityX + ", y = " + velocityY);
        velocityX = velocityX * (-1);
        velocityY = velocityY * (-1);
        //System.out.println("NACHHER => x = " + velocityX + ", y = " + velocityY);
    }

    public void update(double time)
    {
        positionX += velocityX * time;
        positionY += velocityY * time;
    }

    public void render(GraphicsContext gc)
    {
        gc.drawImage( image, positionX, positionY );
    }

    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(positionX,positionY,width,height);
    }

    public boolean intersects(Sprite s)
    {
        return s.getBoundary().intersects( this.getBoundary() );
    }

    public String toString()
    {
        return " Position: [" + positionX + "," + positionY + "]"
                + " Velocity: [" + velocityX + "," + velocityY + "]";
    }
}