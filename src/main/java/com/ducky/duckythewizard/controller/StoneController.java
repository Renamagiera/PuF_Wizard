package com.ducky.duckythewizard.controller;

import com.ducky.duckythewizard.model.Game;
import com.ducky.duckythewizard.model.GameObject;
import com.ducky.duckythewizard.model.Stone;
import com.ducky.duckythewizard.model.config.GameConfig;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.*;

public class StoneController extends Controller {

    private Thread changeTrumpThread;

    public StoneController (Game game) {
        super(game);
    }


    public void initializeStones(GridPane levelGrid) {
        this.addStonesToArrayList();
        this.setStoneImages(levelGrid);
        this.tintAllStones();
        this.getSession().getCardCtrl().addCardToStones();
        this.changeTrumpColor();
    }

    public void changeTrumpColor() {
        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(1, r -> {
            this.changeTrumpThread = Executors.defaultThreadFactory().newThread(r);
            this.changeTrumpThread.setDaemon(true);
            return this.changeTrumpThread;
        });
        Thread t = new Thread(() -> {
            while (this.getSession().getIsRunning()) {
                try {
                    for (Stone stone : this.getSession().getStoneArrayList()) {
                        int sleepTime = (new Random().nextInt(GameConfig.STONE_CHANGE_COLOR_RATE_MAX - GameConfig.STONE_CHANGE_COLOR_RATE_MIN + 1) + GameConfig.STONE_CHANGE_COLOR_RATE_MIN) * 1000;
                        Thread.sleep(sleepTime);
                        if (Platform.isFxApplicationThread()) {
                            this.changeStoneTrump(stone);
                        } else {
                            Platform.runLater(() -> changeStoneTrump(stone));
                        }

                    }
                } catch (InterruptedException e) {
                    while (!this.getSession().getIsRunning()) {
                        try {
                            this.getSession().getFightCtrl().getFightThread().join();
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });
        t.setDaemon(true);
        threadPool.scheduleAtFixedRate(t, GameConfig.STONE_TRUMP_INITIAL_DELAY, GameConfig.STONE_TRUMP_PERIOD, TimeUnit.SECONDS);
    }

    public Thread getChangeTrumpThread() {
        return this.changeTrumpThread;
    }


    public void changeStoneTrump(Stone stone) {
        stone.setIsChangingColor(true);
        String stoneRandomTrumpColorBefore = stone.getRandomTrumpColorStone();
        getSession().getGameColorObject().tintObject(stone.getStoneImgView(), stoneRandomTrumpColorBefore, true);
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(2));
        pauseTransition.setOnFinished(event -> {
            String newTrump = newStoneTrumpColor(stone);
            while (newTrump.equals(stoneRandomTrumpColorBefore)) {
                newTrump = newStoneTrumpColor(stone);
            }
            getSession().getGameColorObject().tintObject(stone.getStoneImgView(), newTrump, false);
            stone.setIsChangingColor(false);
        });
        pauseTransition.play();
    }

    private String newStoneTrumpColor(Stone stone) {
        stone.setRandomTrumpColorStone(getSession().getGameColorObject().generateRandomTrump().getName());
        return stone.getRandomTrumpColorStone();
    }

    private void addStonesToArrayList() {
        GameObject[][] objectGrid = this.getSession().getLevel().getObjectGrid();
        for (int i = 0; i < objectGrid.length; i++) {
            for (int y = 0; y < objectGrid[i].length; y++) {
                if (objectGrid[i][y] instanceof Stone) {
                    this.getSession().getStoneArrayList().add((Stone)objectGrid[i][y]);
                }
            }
        }
    }

    private void tintAllStones() {
        // color the stones to the trump-color
        for (Stone stone : this.getSession().getStoneArrayList()) {
            stone.setRandomTrumpColorStone(this.getSession().getGameColorObject().generateRandomTrump().getName());
            String stoneRandomTrumpColor = stone.getRandomTrumpColorStone();
            this.getSession().getGameColorObject().tintObject(stone.getStoneImgView(), stoneRandomTrumpColor, false);
        }
    }

    private void setStoneImages(GridPane levelGrid) {
        for (int i = 0; i < levelGrid.getChildren().size(); i++) {
            for (int j = 0; j <= 4; j++) {
                if (levelGrid.getChildren().get(i).getId()!=null && levelGrid.getChildren().get(i).getId().equals("stone" + j)) {
                    ImageView stoneImgView = (ImageView) levelGrid.getChildren().get(i);
                    Image stoneImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(GameConfig.STONE_IMAGE_FILE_NAME)));
                    // safe stone-imageview to stone-arraylist
                    this.getSession().getStoneArrayList().get(j).setStoneImgView(stoneImgView);
                    stoneImgView.setImage(stoneImage);
                }
            }
        }
    }

    public void setInactive(Stone stone) {
        stone.setActive(false);
        // starting timer to set stone active again in new thread
        Thread thread = new Thread(() -> {
            try {
                this.getSession().getGameColorObject().setOpacityImageView(stone.getStoneImgView(), 0.4);
                Thread.sleep(GameConfig.STONE_INACTIVE_TIMER);
                stone.setActive(true);
                this.getSession().getGameColorObject().setOpacityImageView(stone.getStoneImgView(), 1);
            } catch (InterruptedException ie) {
                System.out.println(ie);
            }
        });
        thread.start();
    }
}
