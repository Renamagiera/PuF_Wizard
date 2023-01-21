package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.model.config.GameConfig;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Rectangle2D;

public class CollisionHandler {

    private GameObject[][] levelObjectGrid;
    private double tileHeight = GameConfig.LEVEL_CELL_HEIGHT;
    private double tileWidth = GameConfig.LEVEL_CELL_WIDTH;

    private Stone currentCollidedStone;

    public BooleanProperty stoneHit;

    public CollisionHandler(GameObject[][] objectGrid) {
        this.levelObjectGrid = objectGrid;
        this.stoneHit = new SimpleBooleanProperty(false);
    }

    public boolean isCollision(Rectangle2D playerBoundaries) {
        boolean isCollision = false;

        for (int row = 0; row < this.levelObjectGrid.length; row++) {
            for (int column = 0; column < this.levelObjectGrid[row].length; column++) {
                if (this.levelObjectGrid[row][column] != null && playerBoundaries.intersects(getBoundariesOfGameObject(row, column, this.levelObjectGrid[row][column] instanceof Stone))) {
                    if (!this.levelObjectGrid[row][column].getCanPassThrough()) {
                        return isCollision = true;
                    } else {
                        isFightCollision(playerBoundaries, row, column);
                    }
                }
            }
        }
        return false;
    }

    public void isFightCollision(Rectangle2D playerBoundaries, int row, int column) {

        Stone stone = (Stone)this.levelObjectGrid[row][column];
        if(stone.isActive() && !stone.getIsChangingColor()) {
            //this.myGameController.startCollision(stone); //start event here somehow
            this.currentCollidedStone = stone;
            this.stoneHit.set(true);
        }
    }

    private Rectangle2D getBoundariesOfGameObject(int row, int column, boolean stone){
        if (!stone) {
            return new Rectangle2D(column*this.tileWidth, row*this.tileHeight, this.tileWidth, this.tileHeight);
        } else {
            // smaller hit-box for stones
            return new Rectangle2D(column*this.tileWidth + (this.tileWidth/4), row*this.tileHeight + (this.tileHeight/2), this.tileWidth/2, this.tileHeight/2);
        }
    }

    public Stone getCurrentCollidedStone() {
        return this.currentCollidedStone;
    }

}
