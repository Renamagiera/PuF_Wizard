package com.ducky.duckythewizard.view;

import javafx.scene.layout.AnchorPane;

public class GameView {
    // GameScene-view-class: defines display (example: user clicks 'add to cart')
    AnchorPane rootAnchorPaneGame;
    public GameView(AnchorPane rootAnchorPaneGame) {
        this.rootAnchorPaneGame = rootAnchorPaneGame;
    }
    public AnchorPane getRootAnchorPaneGame() {
        return rootAnchorPaneGame;
    }
}
