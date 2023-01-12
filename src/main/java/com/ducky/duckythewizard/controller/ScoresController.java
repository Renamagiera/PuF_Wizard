package com.ducky.duckythewizard.controller;

import com.ducky.duckythewizard.model.HighScore;
import com.ducky.duckythewizard.model.ServerFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import java.io.IOException;


public class ScoresController extends VBox {

    @FXML
    private VBox scoresRoot;
    @FXML
    private TableView scoresTable;
    @FXML
    private TableColumn rankColumn;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn scoreColumn;

    private int limit = 5;
    private ObservableList<HighScore> topHighScoresObservable;
    ServerFacade serverFacade;

    public ScoresController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/com/ducky/duckythewizard/scenes/helpScenes/scores.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.initialize();
    }

    private void initialize() {
        serverFacade = new ServerFacade();

        // getting top n high scores from server
        topHighScoresObservable = FXCollections.observableList(serverFacade.getTopHighScoresFromServer(limit));

        // binding columns to object attributes
        rankColumn.setCellValueFactory(
                new PropertyValueFactory<HighScore, Integer>("rank")
        );
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<HighScore, String>("name")
        );
        scoreColumn.setCellValueFactory(
                new PropertyValueFactory<HighScore, Integer>("score")
        );

        // setting table size and preventing scrolling
        this.setTable();

        // inserting server data into table
        scoresTable.setItems(topHighScoresObservable);
    }

    public boolean setGameScore(int score) {
        // let player enter their name if score is high enough
        if (score > 0 &&
                (topHighScoresObservable.size() < limit || score > topHighScoresObservable.get(limit-1).getScore())) {
            // creating a GridPane container
            GridPane grid = new GridPane();
            grid.setPadding(new Insets(10, 10, 10, 10));
            grid.setVgap(5);
            grid.setHgap(5);
            grid.setLayoutX(225);
            grid.setLayoutY(500);

            // defining a label and the Name text field
            final Label scoreLabel = new Label();
            scoreLabel.setText("Congratulations! New high score!");
            scoreLabel.setTextFill(Color.color(1, 0, 0));
            GridPane.setConstraints(scoreLabel, 0, 0);
            grid.getChildren().add(scoreLabel);
            final TextField name = new TextField();
            name.setPromptText("Enter Your Name");
            name.setPrefColumnCount(10);
            name.getText();
            GridPane.setConstraints(name, 0, 1);
            grid.getChildren().add(name);

            // defining the Submit button
            Button submit = new Button("Submit");
            GridPane.setConstraints(submit, 1, 1);
            grid.getChildren().add(submit);

            // setting an action for the Submit button
            submit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    // sending new high score to server
                    serverFacade.sendHighScoreToServer(name.getText(), score);
                    // getting updated high score list from server
                    topHighScoresObservable =
                            FXCollections.observableList(serverFacade.getTopHighScoresFromServer(limit));
                    scoresTable.setItems(topHighScoresObservable);
                    // removing input text field so player cannot enter their name twice
                    scoresRoot.getChildren().remove(grid);
                }
            });
            scoresRoot.getChildren().add(grid);
            return true;
        } else {
            return false;
        }
    }

    private void setTable() {
        this.scoresTable.setFixedCellSize(50);
        this.scoresTable.prefHeight((50 * topHighScoresObservable.size()) + 30);
        this.scoresTable.addEventFilter(ScrollEvent.ANY, Event::consume);
        this.scoresTable.addEventFilter(MouseEvent.ANY, Event::consume);
    }
}
