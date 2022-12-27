package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.model.color.GameColorObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.AnchorPane;

public class EndSceneView {
    public SimpleStringProperty endSceneLabelProperty;
    public SimpleStringProperty endSceneLabelStyleProperty;
    public SimpleStringProperty endSceneStyleProperty;
    public SimpleStringProperty scoreProperty;
    public SimpleStringProperty scoreStyle;
    private GameColorObject gameColorObject;
    private int score;
    public EndSceneView() {
        this.endSceneLabelProperty = new SimpleStringProperty();
        this.endSceneLabelStyleProperty = new SimpleStringProperty();
        this.endSceneStyleProperty = new SimpleStringProperty();
        this.scoreProperty = new SimpleStringProperty();
    }

    public void renderEndScene(AnchorPane anchorPaneEndScene, boolean duckyWin, GameColorObject gameColorObject, int score) {
        this.gameColorObject = gameColorObject;
        this.score = score;
        anchorPaneEndScene.setVisible(true);
        anchorPaneEndScene.requestFocus();
        setStyles(duckyWin);
    }

    private void setStyles(boolean duckyWin) {
        this.scoreProperty.set("Your score: " + this.score);
        if (duckyWin) {
            this.endSceneLabelProperty.set("WIN");
            this.endSceneLabelStyleProperty.set("-fx-font-size: 50px; -fx-text-fill: " + gameColorObject.getHexCodeFromMap("green") + ";");
            this.endSceneStyleProperty.set("-fx-border-color: " + gameColorObject.getHexCodeFromMap("green") + "; -fx-border-width: 3px;");
        } else {
            this.endSceneLabelProperty.set("LOSS");
            this.endSceneLabelStyleProperty.set("-fx-font-size: 50px; -fx-text-fill: " + gameColorObject.getHexCodeFromMap("red") + ";");
            this.endSceneStyleProperty.set("-fx-border-color: " + gameColorObject.getHexCodeFromMap("red") + "; -fx-border-width: 3px;");
        }
    }
}
