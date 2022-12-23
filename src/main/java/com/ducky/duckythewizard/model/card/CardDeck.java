package com.ducky.duckythewizard.model.card;

import com.ducky.duckythewizard.model.color.GameColor;
import com.ducky.duckythewizard.model.color.GameColorObject;
import com.ducky.duckythewizard.model.config.GameConfig;
import com.ducky.duckythewizard.model.FightView;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.*;

public class CardDeck {
    // how data can be accessed, created, stored and changed

    private static final ArrayList<Card> CARD_DECK = new ArrayList<>();
    private static final Map<String, Integer> CARD_SLOT_POSITION = new HashMap<>();
    private ArrayList<GameColor> trumpColors;
    private GameColor wizard;
    private GameColor none;
    public SimpleStringProperty cardsProperty;

    public CardDeck(GameColorObject gameColorObject) {
        this.trumpColors = gameColorObject.getTrumpColors();
        this.wizard = gameColorObject.getTrumpColorObject().getWizard();
        this.none = gameColorObject.getTrumpColorObject().getNone();
        setCardSlotNumbers();
        addCardsToDeck();
        this.cardsProperty = new SimpleStringProperty(Integer.toString(CARD_DECK.size()));
    }

    public ArrayList<Card> getCardDeck() {
        return CARD_DECK;
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
                CARD_DECK.add(new Card(color, i, imgFileName));
            }
        }
        addWizards();
        shuffleCards();
    }

    private void addWizards() {
        for (int i = 0; i < GameConfig.AMOUNT_WIZARDS; i++) {
            CARD_DECK.add(new Card(this.wizard, GameConfig.WIZARD_POINTS, GameConfig.WIZARD_FILENAME));
        }
    }

    private void shuffleCards() {
        Collections.shuffle(CARD_DECK);
    }

    public ArrayList<Card> dealHandCards(ArrayList<Card> deck) {
        ArrayList<Card> takenCards = new ArrayList<>();
        for (int i = GameConfig.MIN_CARD_VALUE; i < GameConfig.AMOUNT_HAND_CARDS; i++) {
            takenCards.add(deck.remove(0));
        }
        // update deck-size
        this.setCardsProperty(Integer.toString(CARD_DECK.size()));
        return takenCards;
    }

    public Card dealOneNewCardFromDeck() {
        if (CARD_DECK.size() != 0) {
            // update deck-size
            this.setCardsProperty(Integer.toString(CARD_DECK.size()));
            return CARD_DECK.remove(0);
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

    public void renderSpecialCard(ImageView imageView, String type) {
        if (type.equals("empty")) {
            imageView.setPickOnBounds(false);
            newImage(GameConfig.EMPTY_CARD_FILENAME, imageView);
        } else if (type.equals("back")) {
            imageView.setPickOnBounds(true);
            System.out.println("back-card");
        }
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

    public boolean checkIfCardDeckEmpty() {
        return CARD_DECK.size() == 0;
    }

    public Card findCardInHandedCards(String colorName, int value, ArrayList<Card> handCards) {
        for (Card card : handCards) {
            if (card.getColor().getName().equals(colorName) && card.getValue() == value) {
                System.out.println("Card found: " + card.getColor().getName() + ", " + card.getValue());
                return card;
            }
        }
        return null;
    }

    public Card findCardinDeck(String colorName, int value, CardDeck deck) {
        for (Card card : deck.getCardDeck()) {
            if (card.getColor().getName().equals(colorName) && card.getValue() == value) {
                System.out.println("Card found: " + card.getColor().getName() + ", "+card.getValue());
                return card;
            }
        }
        return null;
    }

    public double calcSpaceLeftRight(double spaceBetweenCards, int amountCards, double nodeWidth) {
        double handCardsWithoutSpace = GameConfig.CARD_WIDTH * amountCards;
        double handCardsWithSpace = handCardsWithoutSpace + ((amountCards - 1) * spaceBetweenCards);
        return (nodeWidth - handCardsWithSpace) / 2;
    }

    public void setCardsProperty(String cardsProperty) {
        this.cardsProperty.set(cardsProperty);
    }

    public String getCardsProperty() {
        return cardsProperty.get();
    }

    /***********************************************************************************************************************/
    // DELETE ME LATER
    public static void showAllCardDeckInfo() {
        for (Card card : CARD_DECK) {
            System.out.println(card.getValue() + ", " + card.getColor().getName());
        }
        System.out.println("Cards: " + CARD_DECK.size());
    }

    public static void showHandCards(ArrayList<Card> handCards) {
        for (Card card : handCards) {
            System.out.println(card.getValue() + ", " + card.getColor().getName());
        }
    }
    // DELETE ME LATER
}
