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
    public void initializeStones(GridPane levelGrid) {
        this.levelGrid = levelGrid;
        this.addStonesToArrayList();
        this.setStoneImages(levelGrid);
        this.createRunnable();
        this.tintAllStones(levelGrid);
        this.getSession().getCardCtrl().addCardToStones();
    }

    private void createRunnable() {
        // executorService runs runnable as daemons
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(this.getSession().getStoneArrayList().size(),
                new ThreadFactory() {
                    public Thread newThread(Runnable r) {
                        Thread t = Executors.defaultThreadFactory().newThread(r);
                        t.setDaemon(true);
                        return t;
                    }
                });
        Runnable stoneColorRunnable = new Runnable() {
            public void run() {
                if (getSession().getIsRunning()) {
                    for (Stone stone : getSession().getStoneArrayList()) {
                        stone.setActive(false);
                        stone.setRandomTrumpColorStone(getSession().getGameColorObject().generateRandomTrump());
                        //System.out.println("--> setting " + stone.getId() + " stoneColor to: " + stone.getRandomTrumpColorStone());
                        String stoneRandomTrumpColor = stone.getRandomTrumpColorStone().getName();
                        getSession().getGameColorObject().tintStone(stone.getStoneImgView(), stoneRandomTrumpColor);
                        stone.setActive(true);
                    }
                }
            }
        };
        // change rate is random number generated separately for each stone
        int stoneChangeColorRate = new Random().nextInt(
                GameConfig.STONE_CHANGE_COLOR_RATE_MAX - GameConfig.STONE_CHANGE_COLOR_RATE_MIN + 1) + GameConfig.STONE_CHANGE_COLOR_RATE_MIN;

        executorService.scheduleAtFixedRate(stoneColorRunnable, 0, 5, TimeUnit.SECONDS);
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

    private void tintAllStones(GridPane levelGrid) {
        // color the stones to the trump-color
        for (Stone stone : this.getSession().getStoneArrayList()) {
            stone.setRandomTrumpColorStone(this.getSession().getGameColorObject().generateRandomTrump());
            String stoneRandomTrumpColor = stone.getRandomTrumpColorStone().getName();
            this.getSession().getGameColorObject().tintStone(stone.getStoneImgView(), stoneRandomTrumpColor);
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
}
