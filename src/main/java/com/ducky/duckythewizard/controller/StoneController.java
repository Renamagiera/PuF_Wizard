package com.ducky.duckythewizard.controller;

import com.ducky.duckythewizard.model.Game;
import com.ducky.duckythewizard.model.Stone;
import com.ducky.duckythewizard.model.config.GameConfig;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.*;

public class StoneController extends Controller {
    private GridPane levelGrid;

    public StoneController (Game game) {
        super(game);
    }

    // getter & setter
    public static void setAppIsRunningFalse() {
    }

    public void initializeStones(GridPane levelGrid) {
        this.levelGrid = levelGrid;
        this.addStonesToArrayList();
        this.setStoneImages(levelGrid);
        this.tintAllStones();
        this.getSession().getCardCtrl().addCardToStones();
        this.createRunnable();
    }

    private void createRunnable() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(this.getSession().getStoneArrayList().size(),
                r -> {
                    Thread t = Executors.defaultThreadFactory().newThread(r);
                    t.setDaemon(true);
                    return t;
                });
        Runnable stoneColorRunnable = () -> {
            if (getSession().getIsRunning()) {
                for (Stone stone : getSession().getStoneArrayList()) {
                    if (stone.getActive()) {
                        try {
                            int sleepTime = (new Random().nextInt(4 - 2 + 1) + 2) * 1000;
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        changeStoneTrump(stone);
                    }
                }
            }
        };
        // change rate is random number generated separately for each stone
        int stoneChangeColorRate = new Random().nextInt(
                GameConfig.STONE_CHANGE_COLOR_RATE_MAX - GameConfig.STONE_CHANGE_COLOR_RATE_MIN + 1) + GameConfig.STONE_CHANGE_COLOR_RATE_MIN;
        executorService.scheduleAtFixedRate(stoneColorRunnable, 3, 3, TimeUnit.SECONDS);
    }

    public void changeStoneTrump(Stone stone) {
        String stoneRandomTrumpColorBefore = stone.getRandomTrumpColorStone().getName();
        getSession().getGameColorObject().tintObject(stone.getStoneImgView(), stoneRandomTrumpColorBefore, true);
        Thread t = new Thread(() -> {
            stone.setActive(false);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            stone.setActive(true);
            String newTrump = newStoneTrumpColor(stone);
            while (newTrump.equals(stoneRandomTrumpColorBefore)) {
                newTrump = newStoneTrumpColor(stone);
            }
            getSession().getGameColorObject().tintObject(stone.getStoneImgView(), newTrump, false);
        });
        t.start();
    }

    private String newStoneTrumpColor(Stone stone) {
        stone.setRandomTrumpColorStone(getSession().getGameColorObject().generateRandomTrump());
        return stone.getRandomTrumpColorStone().getName();
    }

    private void addStonesToArrayList() {
        for (int i = 0; i < this.getSession().objectGrid.length; i++) {
            for (int y = 0; y < this.getSession().objectGrid[i].length; y++) {
                if (this.getSession().objectGrid[i][y] instanceof Stone) {
                    this.getSession().getStoneArrayList().add((Stone)this.getSession().objectGrid[i][y]);
                }
            }
        }
    }

    private void tintAllStones() {
        // color the stones to the trump-color
        for (Stone stone : this.getSession().getStoneArrayList()) {
            stone.setRandomTrumpColorStone(this.getSession().getGameColorObject().generateRandomTrump());
            String stoneRandomTrumpColor = stone.getRandomTrumpColorStone().getName();
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
        //stone.setActive(false);
        stone.setIsChangingColor(true);
        // starting timer to set stone active again in new thread
        Thread thread = new Thread(() -> {
            try {
                this.getSession().getGameColorObject().setOpacityImageView(stone.getStoneImgView(), 0.4);
                Thread.sleep(GameConfig.STONE_INACTIVE_TIMER);
                //stone.setActive(true);
                stone.setIsChangingColor(false);
                this.getSession().getGameColorObject().setOpacityImageView(stone.getStoneImgView(), 1);
            } catch (InterruptedException ie) {
                System.out.println(ie);
            }
        });
        thread.start();
    }
}
