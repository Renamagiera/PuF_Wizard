package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.model.color.GameColorObject;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class TextObject {
    private String newText;
    private String fontColorHexCode;
    private String fontSize;
    private String textLabelId;
    private GameColorObject gameColorObject;

    public TextObject(String newText, String fontColorHexCode, String fontSize) {
        this.newText = newText;
        this.fontColorHexCode = fontColorHexCode;
        this.fontSize = fontSize;
    }

    public void setNewText(String newText) {
        this.newText = newText;
    }

    public void setFontColorHexCode(String fontColorHexCode) {
        this.fontColorHexCode = fontColorHexCode;
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

    public String getNewText() {
        return newText;
    }

    public Label newTextCenteredX(AnchorPane anchorPane, int yPosition) {
        Label newTextLabel = new Label();
        newTextLabel.setPrefWidth(anchorPane.getWidth());
        newTextLabel.setAlignment(Pos.CENTER);
        newTextLabel.setLayoutY(yPosition);
        addTextLabel(newTextLabel, yPosition);
        return newTextLabel;
    }

    public void addTextToNodeCustomCoordinates(AnchorPane anchorPane, int xPosition, int yPosition) {
        Label newTextLabel = new Label();
        newTextLabel.setLayoutX(xPosition);
        addTextLabel(newTextLabel, yPosition);
    }

    private void addTextLabel(Label label, int yPosition) {
        label.setLayoutY(yPosition);
        label.setStyle("-fx-font-size: " + this.fontSize + "; -fx-text-fill: " + this.fontColorHexCode);
        label.setText(this.newText);
    }
}
