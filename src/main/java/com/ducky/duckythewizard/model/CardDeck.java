package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.model.config.GameConfig;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class CardDeck {

    private static final ArrayList<Card> CARD_DECK = new ArrayList<>();

    public CardDeck() {}

    public ArrayList<Card> getCardDeck() {
        return CARD_DECK;
    }

    public void addAndRenderALlCards(AnchorPane emptyCardSlots) {
        GameConfig.deckObject.addCardsToDeck();
        GameConfig.handedCards = GameConfig.deckObject.dealHandCards();
        renderHandCardImages(GameConfig.anchorPaneCards);
    }

    public void addCardsToDeck() {
        for (TrumpColor color : GameConfig.trumpColors) {
            for (int i = GameConfig.MIN_CARD_VALUE; i <= GameConfig.MAX_CARD_VALUE; i++) {
                String colorName = color.getName();
                String imgFileName = "/com/ducky/duckythewizard/images/cards/"+colorName+"/"+colorName+i+".png";
                CARD_DECK.add(new Card(color, i+1, imgFileName));
            }
        }
        addWizards();
        shuffleCards();
    }

    public void addWizards() {
        for (int i = 0; i < GameConfig.AMOUNT_WIZARDS; i++) {
            CARD_DECK.add(new Card(GameConfig.colorWizard, GameConfig.WIZARD_POINTS, GameConfig.WIZARD_FILENAME));
        }
    }

    public void shuffleCards() {
        Collections.shuffle(CARD_DECK);
    }

    public ArrayList<Card> dealHandCards() {
        ArrayList<Card> takenCards = new ArrayList<>();
        for (int i = GameConfig.MIN_CARD_VALUE; i < GameConfig.AMOUNT_HAND_CARDS; i++) {
            takenCards.add(CARD_DECK.remove(i));
        }
        return takenCards;
    }

    public void renderHandCardImages(AnchorPane emptyCardSlots) {
        // every Node in AnchorPane: every empty ImageView, as a child from AnchorPane
        int index = 0;
        for (Node imageView : emptyCardSlots.getChildren()) {
            ImageView castedImgView = (ImageView) imageView;
            Image handCardImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(GameConfig.handedCards.get(index).imgFileName())));
            castedImgView.setImage(handCardImage);
            index++;
        }
    }

    public void removeCardAddDummy(int handCardPosition) {
        GameConfig.handedCards.remove(handCardPosition);
        GameConfig.handedCards.add(handCardPosition, new Card(GameConfig.colorNone, 0, GameConfig.EMPTY_CARD_FILENAME));
        // render new hand cards
        // TO-DO-RENATE: nur die ausgewählte Karte neu rendern
        renderHandCardImages(GameConfig.anchorPaneCards);
        GameConfig.playedCards++;
    }

    public Card dealNewCardFromDeck() {
        if (getCardDeck().size() != 0) {
            return getCardDeck().remove(0);
        } else {
            return null;
        }
    }

    public Card findCardInHandedCards(String colorName, int value) {
        for (Card card : GameConfig.handedCards) {
            if (card.color().getName().equals(colorName) && card.value() == value) {
                System.out.println("Card found: "+card.color().getName()+", "+card.value());
                return card;
            }
        }
        return null;
    }

    public Card findCardinDeck(String colorName, int value) {
        for (Card card : getCardDeck()) {
            if (card.color().getName().equals(colorName) && card.value() == value) {
                System.out.println("Card found: "+card.color().getName()+", "+card.value());
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
