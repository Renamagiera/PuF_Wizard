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
        this.positionX = 0;
        this.positionY = 0;
        this.velocityX = 0;
        this.velocityY = 0;
    }

    public double getVelocityX() {
        return this.velocityX;
    }

    public double getVelocityY() {
        return this.velocityY;
    }

    public void setImage(Image i)
    {
        this.image = i;
        this.width = i.getWidth();
        this.height = i.getHeight();
    }

    public void setImage(String filename)
    {
        Image i = new Image(filename);
        this.setImage(i);
    }

    public void setPosition(double x, double y)
    {
        this.positionX = x;
        this.positionY = y;
    }

    public void setVelocity(double x, double y)
    {
//        System.out.println("setVelocity");
//        System.out.println("VORHER => x = " + velocityX + ", y = " + velocityY);
        this.velocityX = x;
        this.velocityY = y;
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
        this.velocityX += x;
        this.velocityY += y;
//        System.out.println("NACHHER => x = " + velocityX + ", y = " + velocityY);
    }

    public void invertVelocity() {
        //System.out.println("VORHER => x = " + velocityX + ", y = " + velocityY);
        this.velocityX = velocityX * (-1);
        this.velocityY = velocityY * (-1);
        //System.out.println("NACHHER => x = " + velocityX + ", y = " + velocityY);
    }

    public void update(double time)
    {
        this.positionX += this.velocityX * time;
        this.positionY += this.velocityY * time;
    }

    public void render(GraphicsContext gc)
    {
        gc.drawImage( this.image, this.positionX, this.positionY );
    }

    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(this.positionX, this.positionY, this.width, this.height);
    }

    public boolean intersects(Sprite s)
    {
        return s.getBoundary().intersects( this.getBoundary() );
    }

    public String toString()
    {
        return " Position: [" + this.positionX + "," + this.positionY + "]"
                + " Velocity: [" + this.velocityX + "," + this.velocityY + "]";
    }
}