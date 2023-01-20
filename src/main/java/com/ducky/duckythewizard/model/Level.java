package com.ducky.duckythewizard.model;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class Level {

    private GameObject[][] objectGrid; //[row][column]


    public Level(GridPane levelGrid) {
        initializeGrid(levelGrid);
    }

    private void initializeGrid(GridPane levelGrid) {
        this.objectGrid = new GameObject[levelGrid.getRowCount()][levelGrid.getColumnCount()];
        for(Node node: levelGrid.getChildren()) {
            int row = levelGrid.getRowIndex(node);
            int column = levelGrid.getColumnIndex(node);
            if(node.getStyleClass().contains("stone")){
                this.objectGrid[row][column] = new Stone();
            }
            else { // if (node.getStyleClass().contains("earth-tile")) // TODO add styleclass to earth tiles??
                this.objectGrid[row][column] = new GameObject(false);
            }
        }
    }

    public GameObject[][] getObjectGrid() {
        return this.objectGrid;
    }
}
