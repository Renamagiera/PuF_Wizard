package com.ducky.duckythewizard.controller;

import com.ducky.duckythewizard.model.GameObject;
import com.ducky.duckythewizard.model.Stone;
import javafx.geometry.Rectangle2D;

public class CollisionHandler {


    private GameController myGameController;
    private GameObject[][] levelObjects;
    private double tileHeight;
    private double tileWidth;

    public CollisionHandler(GameController gameController, GameObject[][] levelObjects, double tileHeight, double tileWidth){
        this.myGameController = gameController;
        this.levelObjects = levelObjects;
        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;
    }

    public boolean isCollision(Rectangle2D playerBoundaries){
        boolean isCollision = false;
        for(int row = 0; row < this.levelObjects.length; row++){
            for(int column = 0; column < this.levelObjects[row].length; column++){
                if(this.levelObjects[row][column] != null && playerBoundaries.intersects(getBoundariesOfGameObject(row, column, this.levelObjects[row][column] instanceof Stone))){
                    if(!this.levelObjects[row][column].getCanPassThrough()){
                        isCollision = true;
                    }
                    else{
                        // collision with stone
                        // inform game, that player collided with stone --> give stone-object
                        //System.out.println("STONE");

                        Stone stone = (Stone)this.levelObjects[row][column];
                        if(stone.isActive()) {
                            this.myGameController.startCollision(stone);
                        }
                    }
                }
            }
        }
        return isCollision;
    }

    private Rectangle2D getBoundariesOfGameObject(int row, int column, boolean stone){
        if (!stone) {
            return new Rectangle2D(column*this.tileWidth, row*this.tileHeight, this.tileWidth, this.tileHeight);
        } else {
            // smaller hit-box for stones
            return new Rectangle2D(column*this.tileWidth + (this.tileWidth/4), row*this.tileHeight + (this.tileHeight/2), this.tileWidth/2, this.tileHeight/2);
        }
    }
}
