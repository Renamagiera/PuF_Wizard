package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.model.color.GameColorObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.AnchorPane;

public class EndSceneView {
    public SimpleStringProperty endSceneLabelProperty;
    public SimpleStringProperty endSceneLabelStyleProperty;
    public SimpleStringProperty endSceneStyleProperty;
    private GameColorObject gameColorObject;
    public EndSceneView() {
        this.endSceneLabelProperty = new SimpleStringProperty();
        this.endSceneLabelStyleProperty = new SimpleStringProperty();
        this.endSceneStyleProperty = new SimpleStringProperty();
    }

    public void renderEndScene(AnchorPane anchorPaneEndScene, boolean duckyWin, GameColorObject gameColorObject) {
        this.gameColorObject = gameColorObject;
        anchorPaneEndScene.setVisible(true);
        anchorPaneEndScene.requestFocus();
        setStyles(duckyWin);
    }

    private void setStyles(boolean duckyWin) {
        if (duckyWin) {
            this.endSceneLabelProperty.set("WIN");
            this.endSceneLabelStyleProperty.set("-fx-font-size: 50px; -fx-text-fill: " + gameColorObject.getHexCodeFromMap("green") + ";");
            this.endSceneStyleProperty.set("-fx-border-color: " + gameColorObject.getHexCodeFromMap("green") + "; -fx-border-width: 3px;");
            System.out.println(gameColorObject.getHexCodeFromMap("green"));
        } else {
            this.endSceneLabelProperty.set("LOSS");
            this.endSceneLabelStyleProperty.set("-fx-font-size: 50px; -fx-text-fill: " + gameColorObject.getHexCodeFromMap("red") + ";");
            this.endSceneStyleProperty.set("-fx-border-color: " + gameColorObject.getHexCodeFromMap("red") + "; -fx-border-width: 3px;");
        }
    }
}
