package com.ducky.duckythewizard.model.card;

import com.ducky.duckythewizard.model.color.GameColor;
import com.ducky.duckythewizard.model.color.GameColorObject;
import com.ducky.duckythewizard.model.config.GameConfig;
import com.ducky.duckythewizard.model.FightScene;
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

    public Card dealOneNewCardFromDeck() {
        if (getCardDeck().size() != 0) {
            return getCardDeck().remove(0);
        } else {
            return null;
        }
    }

    public void renderAllHandCardImages(ArrayList<Card> handCards, AnchorPane anchorPaneCards) {
        for (int i = 0; i < GameConfig.AMOUNT_HAND_CARDS; i++) {
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

    public void renderFightCard(Card card, FightScene fightScene, String player) {
        if (player.equals("ducky")) {
            newImage(card.getImgFileName(), fightScene.getImgViewCardDucky());
        } else if (player.equals("stone")) {
            newImage(card.getImgFileName(), fightScene.getImgViewCardStone());
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
