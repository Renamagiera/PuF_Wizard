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

public class CardDeck {
    // how data can be accessed, created, stored and changed

    private ArrayList<Card> cardDeck;
    private static final Map<String, Integer> CARD_SLOT_POSITION = new HashMap<>();
    private ArrayList<GameColor> trumpColors;
    private GameColor wizard;
    private GameColor none;
    public SimpleStringProperty cardsProperty;

    public CardDeck(GameColorObject gameColorObject) {
        //System.out.println("--> new card deck created");
        this.cardDeck = new ArrayList<>();
        this.trumpColors = gameColorObject.getTrumpColors();
        this.wizard = gameColorObject.getTrumpColorObject().getWizard();
        this.none = gameColorObject.getTrumpColorObject().getNone();
        this.setCardSlotNumbers();
        this.addCardsToDeck();
        this.cardsProperty = new SimpleStringProperty(Integer.toString(cardDeck.size()));
    }

    public ArrayList<Card> getCardDeck() {
        return cardDeck;
    }

    public void setCardSlotNumbers() {
        CARD_SLOT_POSITION.put("handCard0", 0);
        CARD_SLOT_POSITION.put("handCard1", 1);
        CARD_SLOT_POSITION.put("handCard2", 2);
        CARD_SLOT_POSITION.put("handCard3", 3);
        CARD_SLOT_POSITION.put("handCard4", 4);
    }

    private void addCardsToDeck() {
        ArrayList<GameColor> trumpColorsNoNone = new ArrayList<>(this.trumpColors);
        trumpColorsNoNone.remove(this.none);
        for (GameColor color : trumpColorsNoNone) {
            for (int i = GameConfig.MIN_CARD_VALUE; i <= GameConfig.MAX_CARD_VALUE; i++) {
                String colorName = color.getName();
                String imgFileName = "/com/ducky/duckythewizard/images/cards/"+colorName+"/"+colorName+i+".png";
                cardDeck.add(new Card(color, i, imgFileName));
            }
        }
        addWizards();
        shuffleCards();
        //System.out.println("--> card deck size: " + cardDeck.size());
    }

    private void addWizards() {
        for (int i = 0; i < GameConfig.AMOUNT_WIZARDS; i++) {
            cardDeck.add(new Card(this.wizard, GameConfig.WIZARD_POINTS, GameConfig.WIZARD_FILENAME));
        }
    }

    private void shuffleCards() {
        Collections.shuffle(cardDeck);
    }

    public ArrayList<Card> dealHandCards(ArrayList<Card> deck) {
        ArrayList<Card> takenCards = new ArrayList<>();
        for (int i = GameConfig.MIN_CARD_VALUE; i < GameConfig.AMOUNT_HAND_CARDS; i++) {
            takenCards.add(deck.remove(0));
        }
        // update deck-size
        this.setCardsProperty(Integer.toString(cardDeck.size()));
        return takenCards;
    }

    public Card dealOneNewCardFromDeck() {
        if (cardDeck.size() != 0) {
            Card card = cardDeck.remove(0);
            // update deck-size
            this.setCardsProperty(Integer.toString(cardDeck.size()));
            return card;
        } else {
            return null;
        }
    }

    public void renderAllHandCardImages(ArrayList<Card> handCards, AnchorPane anchorPaneCards) {
        double spaceBetweenCards = 40.0;
        double xPosition = calcSpaceLeftRight(spaceBetweenCards, GameConfig.AMOUNT_HAND_CARDS, GameConfig.WINDOW_WIDTH);
        for (int i = 0; i < GameConfig.AMOUNT_HAND_CARDS; i++) {
            ImageView imgView = (ImageView) anchorPaneCards.getChildren().get(i);
            imgView.setLayoutX(xPosition);
            xPosition = xPosition + GameConfig.CARD_WIDTH + spaceBetweenCards;
            newImage(handCards.get(i).getImgFileName(), (ImageView) anchorPaneCards.getChildren().get(i));
        }
    }

    public void renderCard(int clickPosition, ArrayList<Card> handCards, AnchorPane anchorPaneCards) {
        ImageView imageView = (ImageView) anchorPaneCards.getChildren().get(clickPosition);
        imageView.setPickOnBounds(true);
        newImage(handCards.get(clickPosition).getImgFileName(), imageView);
    }

    public void renderEmptyCard(ImageView imageView) {
        imageView.setPickOnBounds(false);
        newImage(GameConfig.EMPTY_CARD_FILENAME, imageView);
    }

    public Card removeHandCard(int handCardPosition, ArrayList<Card> handCards, boolean newCardFromDeck) {
        handCards.remove(handCardPosition);
        if (newCardFromDeck) {
            return dealOneNewCardFromDeck();
        } else {
            return new Card(this.none, 0, GameConfig.EMPTY_CARD_FILENAME);
        }
    }

    private void newImage(String fileName, ImageView imgView) {
        Image newImage =  new Image(Objects.requireNonNull(getClass().getResourceAsStream(fileName)));
        imgView.setImage(newImage);
    }

    public int getHandCardPosition(MouseEvent event) {
        return CARD_SLOT_POSITION.get(((ImageView)event.getSource()).getId());
    }

    public double calcSpaceLeftRight(double spaceBetweenCards, int amountCards, double nodeWidth) {
        double handCardsWithoutSpace = GameConfig.CARD_WIDTH * amountCards;
        double handCardsWithSpace = handCardsWithoutSpace + ((amountCards - 1) * spaceBetweenCards);
        return (nodeWidth - handCardsWithSpace) / 2;
    }

    public void setCardsProperty(String cardsProperty) {
        this.cardsProperty.set(cardsProperty);
    }

}
