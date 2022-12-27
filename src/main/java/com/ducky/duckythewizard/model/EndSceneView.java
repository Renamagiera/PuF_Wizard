package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.model.color.GameColorObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class EndSceneView {
    private AnchorPane anchorPaneEndScene;
    public SimpleStringProperty exitLabelProperty;
    public SimpleStringProperty endSceneLabelProperty;
    public SimpleStringProperty endSceneLabelStyleProperty;
    public SimpleStringProperty endSceneStyleProperty;
    public SimpleStringProperty scoreProperty;
    private GameColorObject gameColorObject;
    private boolean pause = false;
    private int score;
    public EndSceneView() {
        this.endSceneLabelProperty = new SimpleStringProperty();
        this.endSceneLabelStyleProperty = new SimpleStringProperty();
        this.endSceneStyleProperty = new SimpleStringProperty();
        this.scoreProperty = new SimpleStringProperty();
        this.exitLabelProperty = new SimpleStringProperty();
    }

    public void renderEndScene(AnchorPane anchorPaneEndScene, String sceneOverlay, GameColorObject gameColorObject, int score) {
        this.gameColorObject = gameColorObject;
        this.score = score;
        this.anchorPaneEndScene = anchorPaneEndScene;
        this.anchorPaneEndScene.setVisible(true);
        this.anchorPaneEndScene.requestFocus();
        this.setStyles(sceneOverlay);
    }

    private void setStyles(String sceneOverlay) {
        this.scoreProperty.set("Your score: " + this.score);
        switch (sceneOverlay) {
            case "duckyWin" -> {
                this.endSceneLabelProperty.set("WIN");
                this.endSceneLabelStyleProperty.set("-fx-font-size: 50px; -fx-text-fill: " + gameColorObject.getHexCodeFromMap("green") + ";");
                this.endSceneStyleProperty.set("-fx-border-color: " + gameColorObject.getHexCodeFromMap("green") + "; -fx-border-width: 3px;");
            }
            case "duckyLoss" -> {
                this.endSceneLabelProperty.set("LOSS");
                this.endSceneLabelStyleProperty.set("-fx-font-size: 50px; -fx-text-fill: " + gameColorObject.getHexCodeFromMap("red") + ";");
                this.endSceneStyleProperty.set("-fx-border-color: " + gameColorObject.getHexCodeFromMap("red") + "; -fx-border-width: 3px;");
            }
            case "pause" -> {
                this.pause = true;
                this.endSceneLabelProperty.set("Pause");
                this.endSceneLabelStyleProperty.set("-fx-font-size: 50px; -fx-text-fill: " + gameColorObject.getHexCodeFromMap("white") + ";");
                this.endSceneStyleProperty.set("-fx-border-color: " + gameColorObject.getHexCodeFromMap("white") + "; -fx-border-width: 3px;");
                this.exitLabelProperty.set("x");
            }
        }
    }

    public void endScene() {
        if (pause) {
            this.exitLabelProperty.set("");
            this.anchorPaneEndScene.setVisible(false);
            this.pause = false;
        }
    }
}
