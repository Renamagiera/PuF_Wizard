package com.ducky.duckythewizard.controller.scenes;

import com.ducky.duckythewizard.controller.Controller;
import com.ducky.duckythewizard.model.Game;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**This controller-class handles all interactions that interrelate with the end-scene. The end-scene is an AnchorPane.
 * The controller adds Event-Handler and assign them to Elements within the End-Scene AnchorPane*/

public class EndSceneController extends Controller {
    private final Label minLabel;
    private final Label maxLabel;
    private final Label menuLabel;
    private final Label restartLabel;
    private final AnchorPane anchorPaneEndScene;
    private final AnchorPane anchorPaneEndSceneMenu;

    public EndSceneController(Game game) {
        super(game);
        this.minLabel = (Label) this.getSession().getRootAnchorPane().lookup("#exitLabelEndView");
        this.maxLabel = (Label) this.getSession().getRootAnchorPane().lookup("#maximizeLabelEndView");
        this.menuLabel = (Label) this.getSession().getRootAnchorPane().lookup("#backToMenu");
        this.restartLabel = (Label) this.getSession().getRootAnchorPane().lookup("#restart");
        this.anchorPaneEndScene = (AnchorPane) this.getSession().getRootAnchorPane().lookup("#endScene");
        this.anchorPaneEndSceneMenu = (AnchorPane) this.getSession().getRootAnchorPane().lookup("#endSceneMenu");
        this.minLabel.setVisible(false);
        this.maxLabel.setVisible(false);
    }

    public void addMinEventHandler() {
        this.minLabel.setVisible(true);
        EventHandler<MouseEvent> minimize = new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                anchorPaneEndScene.getChildren().add(menuLabel);
                anchorPaneEndScene.getChildren().add(restartLabel);
                anchorPaneEndSceneMenu.getChildren().remove(menuLabel);
                anchorPaneEndSceneMenu.getChildren().remove(restartLabel);
                getSession().getEndSceneView().minimizeEndSceneMenu();
                getSession().getEndSceneView().setMaxLabelLayouts(maxLabel);
                minLabel.setVisible(false);
                maxLabel.setVisible(true);
                addMaxEventHandler();
                minLabel.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
            }
        };
        this.minLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, minimize);
    }

    public void addMaxEventHandler() {
        EventHandler<MouseEvent> maximize = new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                anchorPaneEndSceneMenu.getChildren().add(menuLabel);
                anchorPaneEndSceneMenu.getChildren().add(restartLabel);
                anchorPaneEndScene.getChildren().remove(menuLabel);
                anchorPaneEndScene.getChildren().remove(restartLabel);
                getSession().getEndSceneView().maximizeEndSceneMenu();
                getSession().getEndSceneView().setLabelLayouts(40);
                maxLabel.setVisible(false);
                minLabel.setVisible(true);
                addMinEventHandler();
                maxLabel.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
            }
        };
        this.maxLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, maximize);
    }
}
