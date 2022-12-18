package com.ducky.duckythewizard.controller;

import com.ducky.duckythewizard.model.Game;
import com.ducky.duckythewizard.model.Stone;
import com.ducky.duckythewizard.model.config.GameConfig;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.Objects;

public class StoneController extends Controller {
    public StoneController (Game game) {
        super(game);
    }
    public void initializeStones(GridPane levelGrid) {
        addStonesToArrayList();
        addRandomTrumpColorStone();
        setStoneImages(levelGrid);
        tintStones(levelGrid);
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
            stone.setRandomTrumpColor(this.getSession().getGameColorObject().getRandomTrumpColor());
        }
    }

    private void tintStones(GridPane levelGrid) {
        // color the stones to the trump-color
        for (int i = 0; i < levelGrid.getChildren().size(); i++) {
            for (int x = 0; x < this.getSession().getStoneArrayList().size(); x++) {
                if (levelGrid.getChildren().get(i).getId()!=null && levelGrid.getChildren().get(i).getId().equals("stone" + x)) {
                    Stone stone = this.getSession().getStoneArrayList().get(x);
                    this.getSession().getStoneArrayList().get(x).setRandomTrumpColor(this.getSession().getGameColorObject().getRandomTrumpColor());
                    ImageView imgView = (ImageView) levelGrid.getChildren().get(i);
                    String stoneRandomTrumpColor = stone.getRandomTrumpColor().getName();
/*                    if (!stoneRandomTrumpColor.equals("none")) {
                        this.session.getGameColorObject().tintStone(imgView, stoneRandomTrumpColor);
                    }*/
                    this.getSession().getGameColorObject().tintStone(imgView, stoneRandomTrumpColor);
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
}
