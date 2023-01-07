package com.ducky.duckythewizard.model.card;

import com.ducky.duckythewizard.model.color.GameColor;
import com.ducky.duckythewizard.model.color.GameColorObject;
import com.ducky.duckythewizard.model.config.GameConfig;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.*;

public class CardDeckModel {
    /*Model-class for card-deck. How card-deck-data will be accessed, created, stored and changed.*/

    private ArrayList<CardModel> cardDeck;
    private static final Map<String, Integer> CARD_SLOT_POSITION = new HashMap<>();
    private ArrayList<GameColor> trumpColors;
    private GameColor wizard;
    private GameColor none;
    public SimpleStringProperty cardsProperty;

    public CardDeckModel() {
        this.cardDeck = new ArrayList<>();
        this.createCardSlotTitles();
        this.cardsProperty = new SimpleStringProperty(Integer.toString(cardDeck.size()));
    }

    // getter & setter
    public ArrayList<CardModel> getCardDeck() {
        return cardDeck;
    }
    public void setCardsProperty(String cardsProperty) {
        this.cardsProperty.set(cardsProperty);
    }
    public void setTrumpColors(ArrayList<GameColor> trumpColors) {
        this.trumpColors = trumpColors;
    }
    public void setWizard(GameColor wizard) {
        this.wizard = wizard;
    }
    public void setNone(GameColor none) {
        this.none = none;
    }

    public void createCardSlotTitles() {
        CARD_SLOT_POSITION.put("handCard0", 0);
        CARD_SLOT_POSITION.put("handCard1", 1);
        CARD_SLOT_POSITION.put("handCard2", 2);
        CARD_SLOT_POSITION.put("handCard3", 3);
        CARD_SLOT_POSITION.put("handCard4", 4);
    }

    public void storeCardsInDeckArray() {
        ArrayList<GameColor> trumpColorsNoNone = new ArrayList<>(this.trumpColors);
        trumpColorsNoNone.remove(this.none);
        for (GameColor color : trumpColorsNoNone) {
            for (int i = GameConfig.MIN_CARD_VALUE; i <= GameConfig.MAX_CARD_VALUE; i++) {
                String colorName = color.getName();
                String imgFileName = "/com/ducky/duckythewizard/images/cards/"+colorName+"/"+colorName+i+".png";
                this.cardDeck.add(new CardModel(color, i, imgFileName));
            }
        }
        this.addWizards();
        this.shuffleCards();
    }

    private void addWizards() {
        /*Add wizards to card-deck using fix amount from game-config,
        so amount of wizards can be increased dynamically*/
        for (int i = 0; i < GameConfig.AMOUNT_WIZARDS; i++) {
            cardDeck.add(new CardModel(this.wizard, GameConfig.WIZARD_POINTS, GameConfig.WIZARD_FILENAME));
        }
    }

    private void shuffleCards() {
        Collections.shuffle(cardDeck);
    }

    public void changeAllHandCardImages(ArrayList<CardModel> handCards, AnchorPane anchorPaneCards) {
        double spaceBetweenCards = 40.0;
        double xPosition = calcSpaceLeftRight(spaceBetweenCards, GameConfig.AMOUNT_HAND_CARDS, GameConfig.WINDOW_WIDTH);
        for (int i = 0; i < GameConfig.AMOUNT_HAND_CARDS; i++) {
            ImageView imgView = (ImageView) anchorPaneCards.getChildren().get(i);
            imgView.setLayoutX(xPosition);
            xPosition = xPosition + GameConfig.CARD_WIDTH + spaceBetweenCards;
            createNewImage(handCards.get(i).getImgFileName(), (ImageView) anchorPaneCards.getChildren().get(i));
        }
    }

    public void changeHandCardImage(int clickPosition, ArrayList<CardModel> handCards, AnchorPane anchorPaneCards) {
        ImageView imageView = (ImageView) anchorPaneCards.getChildren().get(clickPosition);
        imageView.setPickOnBounds(true);
        createNewImage(handCards.get(clickPosition).getImgFileName(), imageView);
    }

    public void changeToEmptyCard(ImageView imageView) {
        imageView.setPickOnBounds(false);
        createNewImage(GameConfig.EMPTY_CARD_FILENAME, imageView);
    }

    public int returnHandCardPosition(MouseEvent event) {
        return CARD_SLOT_POSITION.get(((ImageView)event.getSource()).getId());
    }

    public CardModel dealOneNewCardFromDeck() {
        if (this.cardDeck.size() != 0) {
            CardModel card = this.cardDeck.remove(0);
            // update deck-size
            this.setCardsProperty(Integer.toString(this.cardDeck.size()));
            return card;
        } else {
            return null;
        }
    }

    public double calcSpaceLeftRight(double spaceBetweenCards, int amountCards, double nodeWidth) {
        double handCardsWithoutSpace = GameConfig.CARD_WIDTH * amountCards;
        double handCardsWithSpace = handCardsWithoutSpace + ((amountCards - 1) * spaceBetweenCards);
        return (nodeWidth - handCardsWithSpace) / 2;
    }

    private void createNewImage(String fileName, ImageView imgView) {
        Image newImage =  new Image(Objects.requireNonNull(getClass().getResourceAsStream(fileName)));
        imgView.setImage(newImage);
    }
}
