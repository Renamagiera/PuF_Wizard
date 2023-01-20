package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.controller.CollisionController;
import com.ducky.duckythewizard.controller.scenes.MenuController;
import com.ducky.duckythewizard.model.card.Card;
import com.ducky.duckythewizard.model.config.GameConfig;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Player {
    private String name = "testname";
    private ArrayList<Card> handCards;
    private int healthPoints;
    private int playableCards;
    private Game session;
    private AnimatedSprite playerSprite;

    /*bindings*/
    public SimpleIntegerProperty score;
    public SimpleIntegerProperty timerProperty;
    public SimpleStringProperty timerTextLabel;
    public SimpleStringProperty timerLabel;
    public SimpleStringProperty timerLabelStyle;

    public Player() {
        this.resetPlayer();
        this.startTimer();
    }

    public void resetPlayer(){
        this.healthPoints = GameConfig.PLAYER_MAX_HEALTH_POINTS;
        this.score = new SimpleIntegerProperty(0);
        this.timerProperty = new SimpleIntegerProperty(GameConfig.PLAYER_ACTION_TIMER);
        this.timerTextLabel = new SimpleStringProperty();
        this.timerLabel = new SimpleStringProperty();
        this.timerLabelStyle = new SimpleStringProperty();
        this.playableCards = GameConfig.CARDS_AMOUNT_HANDCARDS;
    }

    private void startTimer(){
        Runnable timerRunnable = () -> {
            if (session != null && session.getIsRunning()){
                if (Platform.isFxApplicationThread()) {
                    timerProperty.setValue(timerProperty.getValue() - 1);
                    if (timerProperty.getValue() == 0) {
                        reducePlayerLife();
                    }else if(timerProperty.getValue() == GameConfig.PLAYER_ACTION_TIMER_CRITICAL){
                        updateTimerLabel(true);
                    }
                } else {
                    Platform.runLater(() -> {
                        timerProperty.setValue(timerProperty.getValue() - 1);
                        if (timerProperty.getValue() == 0) {
                            reducePlayerLife();
                        }else if(timerProperty.getValue() == GameConfig.PLAYER_ACTION_TIMER_CRITICAL){
                            updateTimerLabel(true);
                        }
                    });
                }
            }
        };
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1,
            r -> {
                Thread t = Executors.defaultThreadFactory().newThread(r);
                t.setDaemon(true);
                return t;
            }
        );
        executor.scheduleAtFixedRate(timerRunnable, GameConfig.PLAYER_ACTION_TIMER_INITIAL_DELAY, GameConfig.PLAYER_ACTION_TIMER_PERIOD, TimeUnit.SECONDS);
    }

    //getter & setter
    public String getPlayerName() {
        return this.name;
    }
    public AnimatedSprite getPlayerSprite() {
        return this.playerSprite;
    }
    public int getHealthPoints() {
        return this.healthPoints;
    }
    public int getScore() {
        return this.score.getValue();
    }
    public ArrayList<Card> getHandCards() {
        return this.handCards;
    }

    public void setPlayerName(String name) {
        this.name = name;
    }
    public void setHandCards(ArrayList<Card> handCards) {
        this.handCards = handCards;
    }

    public void createPlayerSprite(CollisionController collisionController) {
        this.playerSprite = new AnimatedSprite(collisionController, MenuController.getSpriteSkin(), MenuController.getSpriteSkinColor(), this);
    }
    public void setSession(Game session){
        this.session = session;
    }

    public void addToScore (int points) {
        this.score.set(score.getValue() + points);
        //System.out.println("==> adding " + points + " to score, TOTAL SCORE is now: " + score.getValue());
    }

    public void resetPlayerTimer() {
        this.timerProperty.set(GameConfig.PLAYER_ACTION_TIMER);
        this.updateTimerLabel(false);
    }

    public void updateTimerLabel(boolean critical) {
        if (critical) {
            this.timerTextLabel.set("-fx-text-fill: red; -fx-stroke: red; -fx-stroke-type: OUTSIDE; -fx-stroke-width: 1px;");
            this.timerLabelStyle.set("-fx-text-fill: red; -fx-stroke: red; -fx-stroke-type: OUTSIDE; -fx-stroke-width: 1px;");
        } else {
            this.timerTextLabel.set("-fx-stroke: black; -fx-stroke-type: OUTSIDE; -fx-stroke-width: 2px;");
            this.timerLabelStyle.set("-fx-stroke: black; -fx-stroke-type: OUTSIDE; -fx-stroke-width: 2px;");
        }
    }

    private void reducePlayerLife(){
        this.healthPoints = this.healthPoints <= 0 ? 0 : this.healthPoints - 1;
        this.resetPlayerTimer();
    }

    public void decrementHandCardsCount() {
        this.playableCards--;
    }

    public boolean checkAvailableCards() {
        return this.playableCards >= 1;
    }

}
