package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.model.colors.GameColor;
import com.ducky.duckythewizard.model.colors.GameColorObject;
import com.ducky.duckythewizard.model.colors.TrumpColor;
import com.ducky.duckythewizard.model.config.GameConfig;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.*;

public class CardDeck {

    private static final ArrayList<Card> CARD_DECK = new ArrayList<>();
    private static final Map<String, Integer> CARD_SLOT_POSITION = new HashMap<>();
    private ArrayList<GameColor> trumpColors;
    private GameColor wizard;
    private GameColor none;

    public CardDeck(GameColorObject gameColorObject) {
        this.trumpColors = gameColorObject.getTrumpColors();
        this.wizard = gameColorObject.getTrumpColorObject().getWizard();
        this.none = gameColorObject.getTrumpColorObject().getNone();
        setCardSlotNumbers();
        addCardsToDeck();
    }

    public ArrayList<Card> getCardDeck() {
        return CARD_DECK;
    }

    public void setCardSlotNumbers() {
        CARD_SLOT_POSITION.put("firstCard", 0);
        CARD_SLOT_POSITION.put("secondCard", 1);
        CARD_SLOT_POSITION.put("thirdCard", 2);
        CARD_SLOT_POSITION.put("fourthCard", 3);
        CARD_SLOT_POSITION.put("fifthCard", 4);
    }

    private void addCardsToDeck() {
        ArrayList<GameColor> trumpColorsNoNone = new ArrayList<>(this.trumpColors);
        trumpColorsNoNone.remove(this.none);
        for (GameColor color : trumpColorsNoNone) {
            for (int i = GameConfig.MIN_CARD_VALUE; i <= GameConfig.MAX_CARD_VALUE; i++) {
                String colorName = color.getName();
                String imgFileName = "/com/ducky/duckythewizard/images/cards/"+colorName+"/"+colorName+i+".png";
                CARD_DECK.add(new Card(color, i+1, imgFileName));
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
        return takenCards;
    }

    public void renderAllHandCardImages(ArrayList<Card> handCards, AnchorPane anchorPaneCards) {
        for (int i = 0; i < GameConfig.AMOUNT_HAND_CARDS; i++) {
            renderOneCard(i, handCards, anchorPaneCards);
        }
    }

    public void renderOneCard(int handCardPosition, ArrayList<Card> handCards, AnchorPane anchorPaneCards) {
        ImageView imgView = (ImageView) anchorPaneCards.getChildren().get(handCardPosition);
        //System.out.println("HandCards: " + handCards.size());
        Image handCardImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream(handCards.get(handCardPosition).imgFileName())));
        imgView.setImage(handCardImg);
    }

    public Card removeHandCard(int handCardPosition, ArrayList<Card> handCards, boolean newCardFromDeck) {
        handCards.remove(handCardPosition);
        if (newCardFromDeck) {
            return dealOneNewCardFromDeck();
        } else {
            return new Card(this.none, 0, GameConfig.EMPTY_CARD_FILENAME);
        }
    }

    public Card dealOneNewCardFromDeck() {
        if (getCardDeck().size() != 0) {
            return getCardDeck().remove(0);
        } else {
            return null;
        }
    }

    public int getHandCardPosition(MouseEvent event) {
        return CARD_SLOT_POSITION.get(((ImageView)event.getSource()).getId());
    }

    public Card findCardInHandedCards(String colorName, int value, ArrayList<Card> handCards) {
        for (Card card : handCards) {
            if (card.color().getName().equals(colorName) && card.value() == value) {
                System.out.println("Card found: " + card.color().getName() + ", " + card.value());
                return card;
            }
        }
        return null;
    }

    public Card findCardinDeck(String colorName, int value, CardDeck deck) {
        for (Card card : deck.getCardDeck()) {
            if (card.color().getName().equals(colorName) && card.value() == value) {
                System.out.println("Card found: " + card.color().getName() + ", "+card.value());
                return card;
            }
        }
        return null;
    }


/***********************************************************************************************************************/
    // DELETE ME LATER
    public static void showAllCardDeckInfo() {
        for (Card card : CARD_DECK) {
            System.out.println(card.value() + ", " + card.color().getName());
        }
        System.out.println("Cards: " + CARD_DECK.size());
    }

    public static void showHandCards(ArrayList<Card> handCards) {
        for (Card card : handCards) {
            System.out.println(card.value() + ", " + card.color().getName());
        }
    }
    // DELETE ME LATER
}
