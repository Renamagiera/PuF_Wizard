package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.model.colors.GameColorObject;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class TextObject {
    private String newText;
    private String fontColor;
    private String fontSize;
    private String textLabelId;
    private GameColorObject gameColorObject;

    public TextObject(String newText, String fontColor, String fontSize, GameColorObject gameColorObject) {
        this.newText = newText;
        this.fontColor = fontColor;
        this.fontSize = fontSize;
        this.gameColorObject = gameColorObject;
    }

    public void setNewText(String newText) {
        this.newText = newText;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }


    public void setTextLabelId(String id) {
        this.textLabelId = id;
    }

    public String getTextLabelId() {
        return textLabelId;
    }


    public void addTextToNodeCenterX(AnchorPane anchorPane, int yPosition) {
        Label newTextLabel = new Label();
        newTextLabel.setPrefWidth(anchorPane.getWidth());
        newTextLabel.setAlignment(Pos.CENTER);
        addTextLabel(newTextLabel, anchorPane, yPosition);
    }

    public void addTextToNodeCustomCoordinates(AnchorPane anchorPane, int xPosition, int yPosition) {
        Label newTextLabel = new Label();
        newTextLabel.setLayoutX(xPosition);
        addTextLabel(newTextLabel, anchorPane, yPosition);
    }

    private void addTextLabel(Label label, AnchorPane anchorPane, int yPosition) {
        label.setLayoutY(yPosition);
        label.setStyle("-fx-font-size: " + this.fontSize + "; -fx-text-fill: " + gameColorObject.getHexCodeFromMap(this.fontColor));
        label.setText(this.newText);
        anchorPane.getChildren().add(label);
    }
}
