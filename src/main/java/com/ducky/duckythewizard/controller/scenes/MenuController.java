package com.ducky.duckythewizard.controller.scenes;

import com.ducky.duckythewizard.controller.Controller;
import com.ducky.duckythewizard.controller.WizardMainApplication;
import com.ducky.duckythewizard.model.Host;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MenuController extends Controller {
    /*Controller for all scenes, that are not game-view*/
    @FXML
    private Label nameLabel;
    @FXML
    private BorderPane startBorderPane;
    private Parent root;
    private Map<String, Map<String, String>> skins;
    private ToggleGroup groupSprites;
    private ToggleGroup groupSkins;
    private static String sprite = "ducky";
    private static String skin = "normal";

    @FXML
    public void initialize() {
        if(nameLabel != null) {
            nameLabel.setText(Host.getInstance().getPlayerName());
            nameLabel.setAlignment(Pos.CENTER_RIGHT);
            startBorderPane.getCenter().toBack();
        }
    }

    public MenuController() {
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

    // getter & setter
    public static String getSprite() {
        return sprite;
    }
    public static String getSkin() {
        return skin;
    }
    public void setSprite(String spriteP) {
        sprite = spriteP;
    }
    public void setSkin(String skin) {
        MenuController.skin = skin;
    }

    // TODO maybe this can be in help-scene-ctrl
    @FXML
    private void switchToScene(Event event) throws IOException {
        String labelId = ((Label)event.getSource()).getId();
        String scene = switch (labelId) {
            case "helpButton", "backTo1" -> "/com/ducky/duckythewizard/scenes/helpScenes/helpScene1.fxml";
            case "nextTo2", "backTo2" -> "/com/ducky/duckythewizard/scenes/helpScenes/helpScene2.fxml";
            case "nextTo3" -> "/com/ducky/duckythewizard/scenes/helpScenes/helpScene3.fxml";
            case "controlsButton" -> "/com/ducky/duckythewizard/scenes/helpScenes/settings.fxml";
            case "scoresButton" -> "/com/ducky/duckythewizard/scenes/helpScenes/scoresScene.fxml";
            default -> "";
        };
        this.root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource(scene)));
        this.showWindowStage(event, this.root);
        addRadioButtonsToGroup();
    }

    public void startGame(Event event) throws IOException {
        AnchorPane newRoot = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/com/ducky/duckythewizard/scenes/game-view.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(newRoot);
        newRoot.requestFocus();
    }

    public void backToMenu(Event event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        WizardMainApplication reload = new WizardMainApplication();
        reload.start(stage);
    }

    public void switchToControls(Event event) throws IOException {
        this.root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/com/ducky/duckythewizard/scenes/helpScenes/controls.fxml")));
        this.showWindowStage(event, this.root);
    }

    private void addRadioButtonsToGroup() {
        if (this.root.lookup("#ducky") != null) {
            this.groupSprites = new ToggleGroup();
            this.groupSkins = new ToggleGroup();
            for (String sprite : this.skins.keySet()) {
                RadioButton buttonSprite = (RadioButton) this.root.lookup("#" + sprite);
                buttonSprite.setToggleGroup(groupSprites);
                if (getSprite().equals(sprite)) {
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
                if (getSkin().equals(this.skins.get(spriteId).get(skinKey))) {
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
                    this.setSprite(clickedButton.getText());
                    // reset skin to default (first skin)
                    this.setSkin(this.resetToDefaultSkin());
                    this.updateSkinButtons();
                } else {
                    this.setSkin(clickedButton.getText());
                }
            }
        });
    }

    private String resetToDefaultSkin() {
        String sprite = getSprite();
        return this.skins.get(sprite).get("one");
    }
}