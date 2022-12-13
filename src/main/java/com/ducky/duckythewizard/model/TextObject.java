package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.model.color.GameColorObject;
import com.ducky.duckythewizard.model.config.GameConfig;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.w3c.dom.Text;

public class TextObject {
    private String newText;
    private String fontColorHexCode;
    private String fontSize;
    private String textObjectId;
    private Label textLabel;
    private GameColorObject gameColorObject;

    public TextObject() {
        this.textLabel = new Label();
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


    public void setTextObjectId(String id) {
        this.textObjectId = id;
    }

    public String getTextObjectId() {
        return textObjectId;
    }

    public String getNewText() {
        return newText;
    }

    public Label getTextLabel() {
        return textLabel;
    }

    public void setStyle(String fontSize, String fontColorHexCode) {
        this.textLabel.setStyle("-fx-font-size: " + fontSize + "; -fx-text-fill: " + fontColorHexCode + ";");
    }

    public void setStyleDefaultWhite(String fontSize) {
        this.textLabel.setStyle("-fx-font-size: " + fontSize + ";");
    }

    public void centerLabelFightScene(int yPosition) {
        this.textLabel.setPrefWidth(GameConfig.WINDOW_WIDTH_FIGHT_SCENE);
        this.textLabel.setAlignment(Pos.CENTER);
        this.textLabel.setLayoutY(yPosition);
    }

    public void customCoordinates(int xPosition, int yPosition) {
        this.textLabel.setLayoutX(xPosition);
        this.textLabel.setLayoutY(yPosition);
    }
}
