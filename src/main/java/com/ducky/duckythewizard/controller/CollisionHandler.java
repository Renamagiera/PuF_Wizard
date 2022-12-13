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
        myGameController = gameController;
        this.levelObjects = levelObjects;
        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;
    }

    public boolean isCollision(Rectangle2D playerBoundaries){
        boolean isCollision = false;
        for(int row = 0; row < levelObjects.length; row++){
            for(int column = 0; column < levelObjects[row].length; column++){
                if(levelObjects[row][column] != null && playerBoundaries.intersects(getBoundariesOfGameObject(row, column))){
                    if(!levelObjects[row][column].getCanPassThrough()){
                        isCollision = true;
                    }
                    else{
                        // collision with stone
                        // inform game, that player collided with stone --> give stone-object
                        //System.out.println("STONE");

                        Stone stone = (Stone)levelObjects[row][column];
                        if(stone.isActive()) {
                            myGameController.startCollision(stone);
                        }
                    }
                }
            }
        }
        return isCollision;
    }

    private Rectangle2D getBoundariesOfGameObject(int row, int column){
        return new Rectangle2D(column*tileWidth, row*tileHeight, tileWidth, tileHeight);
    }
}
