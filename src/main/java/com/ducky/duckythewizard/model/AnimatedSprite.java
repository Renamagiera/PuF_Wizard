package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.model.config.GameConfig;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class AnimatedSprite extends Sprite
{
    public Image[] frames;
    private double duration;

    private int walkImageAmount;
    private int flyImageAmount;
    private int idleImageAmount ;

    private String spriteSkinString;
    private String spriteSkinColorString;
    private Player player;
    private CollisionHandler collisionHandler;

    private Image[] imageArrayFlyRight;
    private Image[] imageArrayFlyLeft;
    private Image[] imageArrayIdleRight;
    private Image[] imageArrayIdleLeft;
    private Image[] imageArrayWalkRight;
    private Image[] imageArrayWalkLeft;

    private boolean spriteLooksLeft = false;

    public AnimatedSprite(CollisionHandler collisionHandler, String spriteSkinString, String spriteSkinColorString, Player player) {
        this.spriteSkinString = spriteSkinString;
        this.spriteSkinColorString = spriteSkinColorString;
        this.player = player;
        this.player.resetPlayerTimer();
        this.collisionHandler = collisionHandler;
        this.initializeFileAmounts();
        this.initializeImageArrays();
        this.frames = imageArrayFlyRight;
    }
    public Image getFrame(double time)
    {
        int index = (int)((time % (frames.length * duration)) / duration);
        return frames[index];
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public double getDuration() { return duration;}

    @Override
    public void update(double time)
    {
        // if collision --> revert position and adjust velocity, depending on direction in which collision occurred
        this.positionX += this.velocityX * time;
        if(this.collisionHandler.isCollision(this.getBoundary())){
            this.positionX -= this.velocityX * time;  // revert position
            this.velocityX = this.velocityX * (-1);   // invert velocity
        }

        this.positionY += this.velocityY * time;

        if(this.collisionHandler.isCollision(this.getBoundary())){
            this.positionY -= this.velocityY * time;  // revert position
            if (this.velocityY > 0){             // if Ducky was falling, stop falling
                this.velocityY = 0;
            }
            else {
                this.velocityY = 100;            // if Ducky was flying upwards, invert velocity
            }
        }

        // set animation frames according to movement
        if (this.velocityY == 0 && this.velocityX != 0) {
            this.setFramesForWalking();
        }
        else if (this.velocityX == 0 && this.velocityY == 0) {
            this.setFramesForIdling();
        }
        else {
            this.setFramesForFlying();
        }
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    private void setFramesForWalking() {
        if (this.velocityX > 0) {
            this.spriteLooksLeft = false;
            this.frames = imageArrayWalkRight;
        } else if (velocityX < 0) {
            this.spriteLooksLeft = true;
            this.frames = imageArrayWalkLeft;
        }
    }

    private void setFramesForIdling() {
        if (this.spriteLooksLeft) {
            this.frames = imageArrayIdleLeft;
        } else {
            this.frames = imageArrayIdleRight;
        }
    }

    private void setFramesForFlying() {
        if (this.velocityX > 0) {
            this.spriteLooksLeft = false;
            this.frames = imageArrayFlyRight;
        } else if (this.velocityX < 0){
            this.spriteLooksLeft = true;
            this.frames = imageArrayFlyLeft;
        } else {
            // ducky is just falling
            if (this.spriteLooksLeft) {
                this.frames = imageArrayFlyLeft;
            } else {
                this.frames = imageArrayFlyRight;
            }
        }
    }

    public void translateKeyPressesIntoMovement(ArrayList<String> input) {
        if (input.contains("UP") || input.contains("W")) {
            this.setVelocityY(-100);   // moving UP
        } else {
            this.setVelocityY(100);    // falling DOWN
        }
        if (input.contains("LEFT") || input.contains("A")) {
            this.setVelocityX(-100);   // moving LEFT
        } else if (input.contains("RIGHT") || input.contains("D")) {
            this.setVelocityX(100);    // moving RIGHT
        } else {
            this.setVelocityX(0);      // not moving left or right
        }

    }

    public void checkLevelBoundaryContact(double t) {
        // bouncing back from left level boundary
        if (this.getPositionX() <= 0) {
            this.setVelocity(100, 0);
        }
        // bouncing back from right level boundary
        if (this.getPositionX() >= GameConfig.WINDOW_WIDTH - this.getFrame(t).getWidth()) {
            this.setVelocity(-100, 0);
        }
        // bouncing back from upper level boundary
        if (this.getPositionY() <= 0) {
            this.setVelocity(0, 100);
        }
    }

    public int rescaleImgWidth(Image image) {
        int imgWidth = (int) image.getWidth();
        double calculation = imgWidth * GameConfig.SPRITE_SCALE_FACTOR;
        return (int) calculation;
    }

    public int rescaleImgHeight(Image image) {
        int imgHeight = (int) image.getHeight();
        double calculation = imgHeight * GameConfig.SPRITE_SCALE_FACTOR;
        return (int) calculation;
    }

    private void initializeImageArrays() {
        imageArrayFlyRight = new Image[this.flyImageAmount];
        imageArrayFlyLeft = new Image[this.flyImageAmount];

        imageArrayIdleRight = new Image[this.idleImageAmount];
        imageArrayIdleLeft = new Image[this.idleImageAmount];

        imageArrayWalkRight = new Image[this.walkImageAmount];
        imageArrayWalkLeft = new Image[this.walkImageAmount];

        this.addImages("fly", imageArrayFlyRight, imageArrayFlyLeft);
        this.addImages("idle", imageArrayIdleRight, imageArrayIdleLeft);
        this.addImages("walk", imageArrayWalkRight, imageArrayWalkLeft);
    }

    private void addImages(String move, Image[] right, Image[] left) {
        // add images to movement
        for (int i = 0; i < right.length; i++) {
            Image image = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/com/ducky/duckythewizard/images/" + this.spriteSkinString + "/" + this.spriteSkinColorString + "/" + move + "/" + move + "_" + i + ".png")));
            right[i] = scaleImage(image, rescaleImgWidth(image), rescaleImgHeight(image), true, false);
        }
        // add mirrored images to opposite movement
        for (int i = 0; i < right.length; i++) {
            left[i] = scaleImage(right[i], right[i].getWidth(), right[i].getHeight(), true, true);
        }
    }

    private Image scaleImage(Image source, double targetWidth, double targetHeight, boolean preserveRatio, boolean mirror) {
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        ImageView imageView = new ImageView(source);
        imageView.setPreserveRatio(preserveRatio);
        imageView.setFitWidth(targetWidth);
        imageView.setFitHeight(targetHeight);
        if (mirror) {
            imageView.setScaleX(-1);
        }
        return imageView.snapshot(parameters, null);
    }

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

    private int countFiles(String move) {
        int fileCount;
        File directory = new File("src/main/resources/com/ducky/duckythewizard/images/" + this.spriteSkinString + "/" + this.spriteSkinColorString + "/" + move);
        fileCount = Objects.requireNonNull(directory.list()).length;
        return fileCount;
    }

    private void initializeFileAmounts() {
        this.flyImageAmount = countFiles("fly");
        this.idleImageAmount = countFiles("idle");
        this.walkImageAmount = countFiles("walk");
    }
}
