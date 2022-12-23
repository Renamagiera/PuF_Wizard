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
    private Thread trumpColorChange;
    public StoneController (Game game) {
        super(game);
    }
    public void initializeStones(GridPane levelGrid) {
        addStonesToArrayList();
        //addRandomTrumpColorStone();
        setStoneImages(levelGrid);
        tintAllStones(levelGrid);
        this.getSession().getCardCtrl().addCardToStones();
    }

    private void addStonesToArrayList() {
        for (int i = 0; i < this.getSession().objectGrid.length; i++) {
            for (int y = 0; y < this.getSession().objectGrid[i].length; y++) {
                if (this.getSession().objectGrid[i][y] instanceof Stone) {
                    this.getSession().stoneArrayList.add((Stone)this.getSession().objectGrid[i][y]);
                }
            }
        }
    }

    private void addRandomTrumpColorStone() {
        for (Stone stone : this.getSession().stoneArrayList) {
            stone.setTrumpColorStone(this.getSession().getGameColorObject().generateRandomTrump());
        }
    }

    private void tintAllStones(GridPane levelGrid) {
        // color the stones to the trump-color
        // executorService runs runnables as daemons
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(this.getSession().stoneArrayList.size(),
            new ThreadFactory() {
                public Thread newThread(Runnable r) {
                    Thread t = Executors.defaultThreadFactory().newThread(r);
                    t.setDaemon(true);
                    return t;
                }
            });
        for (int i = 0; i < levelGrid.getChildren().size(); i++) {
            for (int x = 0; x < this.getSession().getStoneArrayList().size(); x++) {
                if (levelGrid.getChildren().get(i).getId()!=null && levelGrid.getChildren().get(i).getId().equals("stone" + x)) {
                    Stone stone = this.getSession().getStoneArrayList().get(x);

                    // set random trump color
                    stone.setTrumpColorStone(this.getSession().getGameColorObject().generateRandomTrump());

                    // generate first random-trump-color
                    this.getSession().getStoneArrayList().get(x).setTrumpColorStone(this.getSession().getGameColorObject().generateRandomTrump());

                    ImageView imgView = (ImageView) levelGrid.getChildren().get(i);
                    // safe stone-imageview to stone-arraylist
                    this.getSession().stoneArrayList.get(x).setStoneImgView(imgView);

                    String stoneRandomTrumpColor = stone.getTrumpColorStone().getName();
                    this.getSession().getGameColorObject().tintStone(imgView, stoneRandomTrumpColor);

                    Runnable stoneColorRunnable = new Runnable() {
                        public void run() {
                            if (getSession().getIsRunning()) {
                                stone.setTrumpColorStone(getSession().getGameColorObject().generateRandomTrump());
                                System.out.println("--> setting " + stone.getId() + " stoneColor to: " + stone.getTrumpColorStone());
                                String stoneRandomTrumpColor = stone.getTrumpColorStone().getName();
                                getSession().getGameColorObject().tintStone(imgView, stoneRandomTrumpColor);
                            }
                        }
                    };

                    // change rate is random number generated separately for each stone
                    int stoneChangeColorRate = new Random().nextInt(
                            GameConfig.STONE_CHANGE_COLOR_RATE_MAX - GameConfig.STONE_CHANGE_COLOR_RATE_MIN + 1) + GameConfig.STONE_CHANGE_COLOR_RATE_MIN;

                    executorService.scheduleAtFixedRate(stoneColorRunnable, 0, stoneChangeColorRate, TimeUnit.SECONDS);
                }
            }
        }
    }

    private void setStoneImages(GridPane levelGrid) {
        for (int i = 0; i < levelGrid.getChildren().size(); i++) {
            for (int j = 0; j <= 4; j++) {
                if (levelGrid.getChildren().get(i).getId()!=null && levelGrid.getChildren().get(i).getId().equals("stone" + j)) {
                    ImageView stoneImgView = (ImageView) levelGrid.getChildren().get(i);
                    Image stoneImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(GameConfig.STONE_IMAGE_FILE_NAME)));
                    stoneImgView.setImage(stoneImage);
                }
            }
        }
    }

    public void setNewTrump() throws InterruptedException {
        this.trumpColorChange = new Thread(() -> {
            while (this.getSession().getIsRunning()) {
                try {
                    Thread.sleep(GameConfig.TRUMP_TIMER);
                    for (Stone stone : this.getSession().stoneArrayList) {
                        stone.setTrumpColorStone(this.getSession().getGameColorObject().generateRandomTrump());
                        this.getSession().getGameColorObject().tintStone(stone.getStoneImgView(), stone.getTrumpColorStone().getName());
                        this.getSession().getFightView().updateBorderColor(stone.getTrumpColorStone().getHexCode());
                        System.out.println("stone id: " + stone.getId() + ", new trump color: " + stone.getTrumpColorStone().getName());
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("");
            }
        });
        this.trumpColorChange.start();
    }
}
