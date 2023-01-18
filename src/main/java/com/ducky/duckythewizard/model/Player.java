package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.model.card.CardModel;
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
    private ArrayList<CardModel> handCards;
    private int healthPoints;
    private int maxHealthPoints = GameConfig.PLAYER_MAX_HEALTH_POINTS;
    private int playableCards;

    public SimpleIntegerProperty score;
    public SimpleIntegerProperty timerProperty;
    public SimpleStringProperty timerTextLabel;
    public SimpleStringProperty timerLabel;
    public SimpleStringProperty timerLabelStyle;

    private Game session;
    private AnimatedSprite playerSprite;

    public Player() {
        this.resetPlayer();
        this.startTimer();
    }

    public void resetPlayer(){
        this.healthPoints = maxHealthPoints;
        this.score = new SimpleIntegerProperty(0);
        this.timerProperty = new SimpleIntegerProperty(GameConfig.PLAYER_ACTION_TIMER);
        this.timerTextLabel = new SimpleStringProperty();
        this.timerLabel = new SimpleStringProperty();
        this.timerLabelStyle = new SimpleStringProperty();
        this.playableCards = GameConfig.AMOUNT_HAND_CARDS;
    }

    private void startTimer(){
        Runnable timerRunnable = new Runnable() {
            public void run() {
                if (session != null && session.getIsRunning()){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            timerProperty.setValue(timerProperty.getValue() - 1);
                            if (timerProperty.getValue() == 0) {
                                reducePlayerLife();
                            }else if(timerProperty.getValue() == 3){
                                updateTimerLabel(true);
                            }
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
        executor.scheduleAtFixedRate(timerRunnable, 0, 1, TimeUnit.SECONDS);
    }

    //getter & setter

    public void setPlayerName(String name) {
        this.name = name;
    }
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
    public ArrayList<CardModel> getHandCards() {
        return this.handCards;
    }
    public int getPlayableCards() {
        return this.playableCards;
    }

    public void setHandCards(ArrayList<CardModel> handCards) {
        this.handCards = handCards;
    }
    public void setPlayerSprite(AnimatedSprite sprite) {
        this.playerSprite = sprite;
    }
    public void setPlayableCards(int playableCards) {
        this.playableCards = playableCards;
    }

    public void setSession(Game session){
        this.session = session;
    }

    public void addToScore (int points) {
        this.score.set(score.getValue() + points);
        System.out.println("==> adding " + points + " to score, TOTAL SCORE is now: " + score.getValue());
    }

    public void resetPlayerTimer() {
        this.timerProperty.set(GameConfig.PLAYER_ACTION_TIMER);
        this.updateTimerLabel(false);
    }

    private void reducePlayerLife(){
        this.healthPoints = this.healthPoints <= 0 ? 0 : this.healthPoints - 1;
        this.resetPlayerTimer();
    }

    public void decrementHandCards() {
        this.playableCards--;
    }

    public boolean checkAvailableCards() {
        int cardsAvailable = GameConfig.AMOUNT_HAND_CARDS;
        for (CardModel handCard : this.handCards) {
            if (handCard.getColor().getName().equals("none")) {
                cardsAvailable--;
            }
        }
        return cardsAvailable >= 1;
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
}
