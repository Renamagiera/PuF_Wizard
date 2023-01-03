package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.controller.GameController;
import com.ducky.duckythewizard.model.color.GameColorObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class EndSceneView {
    private AnchorPane anchorPaneEndScene;
    private GameController gameCtrl;

    private EventHandler<MouseEvent> exitEvent;

    public SimpleStringProperty endSceneLabelProperty;
    public SimpleStringProperty endSceneLabelStyleProperty;
    public SimpleStringProperty endSceneStyleProperty;
    public SimpleStringProperty scoreProperty;
    private GameColorObject gameColorObject;
    private boolean pause = false;
    private boolean gameOver = false;
    private int score;

    private Label exitLabel;

    public EndSceneView() {
        this.endSceneLabelProperty = new SimpleStringProperty();
        this.endSceneLabelStyleProperty = new SimpleStringProperty();
        this.endSceneStyleProperty = new SimpleStringProperty();
        this.scoreProperty = new SimpleStringProperty();
    }

    public void renderEndScene(AnchorPane anchorPaneEndScene, String sceneOverlay, GameColorObject gameColorObject, int score, GameController gameCtrl) {
        this.gameCtrl = gameCtrl;
        this.gameColorObject = gameColorObject;
        this.score = score;
        this.anchorPaneEndScene = anchorPaneEndScene;
        this.anchorPaneEndScene.setVisible(true);
        this.anchorPaneEndScene.requestFocus();
        this.setStyles(sceneOverlay);
        this.addExitEventHandler();
    }

    private void setStyles(String sceneOverlay) {
        this.scoreProperty.set("Your score: " + this.score);
        this.addExitLabel();
        switch (sceneOverlay) {
            case "duckyWin" -> {
                this.pause = false;
                this.gameOver = true;
                this.endSceneLabelProperty.set("WIN");
                this.endSceneLabelStyleProperty.set("-fx-font-size: 50px; -fx-text-fill: " + gameColorObject.getHexCodeFromMap("green") + ";");
                this.endSceneStyleProperty.set("-fx-border-color: " + gameColorObject.getHexCodeFromMap("green") + "; -fx-border-width: 3px;");
            }
            case "duckyLoss" -> {
                this.pause = false;
                this.gameOver = true;
                this.endSceneLabelProperty.set("LOSS");
                this.endSceneLabelStyleProperty.set("-fx-font-size: 50px; -fx-text-fill: " + gameColorObject.getHexCodeFromMap("red") + ";");
                this.endSceneStyleProperty.set("-fx-border-color: " + gameColorObject.getHexCodeFromMap("red") + "; -fx-border-width: 3px;");
            }
            case "pause" -> {
                this.pause = true;
                this.endSceneLabelProperty.set("Pause");
                this.endSceneLabelStyleProperty.set("-fx-font-size: 50px; -fx-text-fill: " + gameColorObject.getHexCodeFromMap("white") + ";");
                this.endSceneStyleProperty.set("-fx-border-color: " + gameColorObject.getHexCodeFromMap("white") + "; -fx-border-width: 3px;");
            }
        }
    }

    public void endScene() {
        if (this.pause) {
            this.anchorPaneEndScene.setVisible(false);
            this.pause = false;
        } else {
            Label menuLabel = (Label) this.anchorPaneEndScene.lookup("#backToMenu");
            Label restartLabel = (Label) this.anchorPaneEndScene.lookup("#restart");
            AnchorPane endScene = (AnchorPane) this.anchorPaneEndScene.lookup("#endSceneMenu");
            this.anchorPaneEndScene.getChildren().add(menuLabel);
            this.anchorPaneEndScene.getChildren().add(restartLabel);
            menuLabel.setLayoutY(60);
            menuLabel.setLayoutX(250);
            restartLabel.setLayoutY(60);
            restartLabel.setLayoutX(435);
            endScene.setVisible(false);
        }
    }

    private void addExitEventHandler() {
        this.exitEvent = mouseEvent -> {
            if (this.exitEvent != null) {
                exitLabel.removeEventHandler(MouseEvent.MOUSE_CLICKED, this.exitEvent);
            }
            this.endScene();
            if (!gameOver) { gameCtrl.resumeGame(); }
        };
        this.exitLabel.addEventFilter(MouseEvent.MOUSE_CLICKED, exitEvent);
    }

    public void addExitLabel() {
        this.exitLabel = new Label("x");
        this.exitLabel.setId("exitLabelEndScene");
        this.exitLabel.setLayoutX(458);
        this.exitLabel.setLayoutY(5);
        this.exitLabel.setStyle("-fx-font-family: 'Alagard'; -fx-text-fill: white; -fx-font-size: 30px;");
        AnchorPane menu = (AnchorPane) this.anchorPaneEndScene.lookup("#endSceneMenu");
        menu.getChildren().add(this.exitLabel);
    }
}
