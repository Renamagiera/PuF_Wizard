package com.ducky.duckythewizard.model;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class Level {

    private GameObject[][] gameObjectGrid;


    public Level(GridPane levelGrid) {
        initializeGrid(levelGrid);
    }

    private void initializeGrid(GridPane levelGrid) {
        this.gameObjectGrid = new GameObject[levelGrid.getRowCount()][levelGrid.getColumnCount()];
        for(Node node: levelGrid.getChildren()) {
            int row = levelGrid.getRowIndex(node);
            int column = levelGrid.getColumnIndex(node);
            if(node.getStyleClass().contains("rock")){
                this.gameObjectGrid[row][column] = new Stone();
            }
            else { // if (node.getStyleClass().contains("earth-tile")) // TODO add styleclass to earth tiles??
                this.gameObjectGrid[row][column] = new GameObject(false);
            }
        }
    }

    public GameObject[][] getGameObjectGrid() {
        return gameObjectGrid;
    }
}
