package com.ducky.duckythewizard.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;
import java.util.*;

public class HelpSceneController extends SceneController {
    private Parent root;
    private Map<String, Map<String, String>> skins;
    private ToggleGroup groupSprites;
    private ToggleGroup groupSkins;

    public HelpSceneController() {
        this.skins = new HashMap<>();
        skins.put("ducky", new HashMap<>() {{
            put("one", "normal");
            put("two", "blue");
            put("three", "red");
        }});
        skins.put("batty", new HashMap<>() {{
            put("one", "normal");
            put("two", "white");
            put("three", "green");
        }});
    }

    @FXML
    private void switchToScene(Event event) throws IOException {
        String labelId = ((Label)event.getSource()).getId();
        String scene = switch (labelId) {
            case "helpButton", "backTo1" -> "/com/ducky/duckythewizard/scenes/helpScenes/helpScene1.fxml";
            case "nextTo2", "backTo2" -> "/com/ducky/duckythewizard/scenes/helpScenes/helpScene2.fxml";
            case "nextTo3" -> "/com/ducky/duckythewizard/scenes/helpScenes/helpScene3.fxml";
            case "controlsButton" -> "/com/ducky/duckythewizard/scenes/helpScenes/settings.fxml";
            default -> "";
        };
        this.root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource(scene)));
        super.getWindowStage(event, this.root);
        addRadioButtonsToGroup();
    }

    public void startGame(Event event) throws IOException {
        super.startGame(event);
    }

    public void backToMenu(Event event) throws IOException {
        super.backToMenu(event);
    }

    public void switchToControls(Event event) throws IOException {
        this.root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/com/ducky/duckythewizard/scenes/helpScenes/controls.fxml")));
        super.getWindowStage(event, this.root);
    }

    private void addRadioButtonsToGroup() {
        if (this.root.lookup("#ducky") != null) {
            this.groupSprites = new ToggleGroup();
            this.groupSkins = new ToggleGroup();
            for (String sprite : this.skins.keySet()) {
                RadioButton buttonSprite = (RadioButton) this.root.lookup("#" + sprite);
                buttonSprite.setToggleGroup(groupSprites);
                if (SceneController.getSprite().equals(sprite)) {
                    buttonSprite.setSelected(true);
                }
            }
            this.updateSkinButtons();
            this.addListener(groupSprites, true);
            this.addListener(groupSkins, false);
        }
    }

    private void updateSkinButtons() {
        RadioButton button = (RadioButton) this.groupSprites.getSelectedToggle();
        String spriteId = button.getId();
        for (String skinKey : this.skins.get(spriteId).keySet()) {
            if (this.root.lookup("#" + skinKey) != null) {
                RadioButton buttonSkin = (RadioButton) this.root.lookup("#" + skinKey);
                buttonSkin.setToggleGroup(this.groupSkins);
                buttonSkin.setText(this.skins.get(spriteId).get(skinKey));
                if (SceneController.getSkin().equals(this.skins.get(spriteId).get(skinKey))) {
                    buttonSkin.setSelected(true);
                }
            }
        }
    }

    private void addListener(ToggleGroup toggleGroup, boolean sprite) {
        toggleGroup.selectedToggleProperty().addListener((observableValue, toggle, t1) -> {
            RadioButton clickedButton = (RadioButton) toggleGroup.getSelectedToggle();
            if (clickedButton != null) {
                if (sprite) {
                    super.setSprite(clickedButton.getText());
                    // reset skin to default (first skin)
                    super.setSkin(this.resetToDefaultSkin());
                    this.updateSkinButtons();
                } else {
                    super.setSkin(clickedButton.getText());
                }
            }
        });
    }

    private String resetToDefaultSkin() {
        String sprite = SceneController.getSprite();
        return this.skins.get(sprite).get("one");
    }
}