package com.ducky.duckythewizard.model.card;

import com.ducky.duckythewizard.model.color.GameColor;
import com.ducky.duckythewizard.model.config.GameConfig;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.util.*;

public class CardDeck {
    /*Model-class for card-deck. How card-deck-data will be accessed, created, stored and changed.*/

    private final ArrayList<Card> cardDeck;
    private ArrayList<GameColor> trumpColors;
    private static final Map<String, Integer> CARD_SLOT_POSITION = new HashMap<>();
    private static final Image CARDS_EMPTY_CARD_IMAGE = new Image(Objects.requireNonNull(CardDeck.class.getResourceAsStream(GameConfig.CARDS_EMPTY_CARD_FILENAME)));

    private GameColor wizard;
    private GameColor none;

    /*bindings*/
    public SimpleStringProperty cardsProperty;
    public ImageView handCard0;
    public ImageView handCard1;
    public ImageView handCard2;
    public ImageView handCard3;
    public ImageView handCard4;

    private final ArrayList<ImageView> handCardsImages = new ArrayList<>();

    public CardDeck() {
        this.cardDeck = new ArrayList<>();
        this.createCardSlotTitles();
        this.cardsProperty = new SimpleStringProperty(Integer.toString(cardDeck.size()));
        this.handCard0 = new ImageView(CARDS_EMPTY_CARD_IMAGE);
        this.handCard1 = new ImageView(CARDS_EMPTY_CARD_IMAGE);
        this.handCard2 = new ImageView(CARDS_EMPTY_CARD_IMAGE);
        this.handCard3 = new ImageView(CARDS_EMPTY_CARD_IMAGE);
        this.handCard4 = new ImageView(CARDS_EMPTY_CARD_IMAGE);
        this.handCardsImages.add(this.handCard0);
        this.handCardsImages.add(this.handCard1);
        this.handCardsImages.add(this.handCard2);
        this.handCardsImages.add(this.handCard3);
        this.handCardsImages.add(this.handCard4);
    }

    // getter & setter
    public ArrayList<Card> getCardDeck() {
        return cardDeck;
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

    private void createCardSlotTitles() {
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
            for (int i = GameConfig.CARDS_VALUE_MIN; i <= GameConfig.CARDS_VALUE_MAX; i++) {
                String colorName = color.getName();
                String imgFileName = "/com/ducky/duckythewizard/images/cards/"+colorName+"/"+colorName+i+".png";
                this.cardDeck.add(new Card(color, i, imgFileName));
            }
        }
        this.addWizards();
        this.shuffleCards();
    }

    private void addWizards() {
        /*Add wizards to card-deck using fix amount from game-config,
        so amount of wizards can be increased dynamically*/
        for (int i = 0; i < GameConfig.CARDS_AMOUNT_WIZARDS; i++) {
            cardDeck.add(new Card(this.wizard, GameConfig.CARDS_VALUE_WIZARD, GameConfig.CARDS_WIZARD_FILENAME));
        }
    }

    private void shuffleCards() {
        Collections.shuffle(cardDeck);
    }

    public int returnHandCardPosition(MouseEvent event) {
        return CARD_SLOT_POSITION.get(((ImageView)event.getSource()).getId());
    }

    public void updateAllHandCardImages(ArrayList<Card> handCards) {
        for (int i = 0; i < GameConfig.CARDS_AMOUNT_HANDCARDS; i++) {
            this.handCardsImages.get(i).imageProperty().set(new Image(Objects.requireNonNull(getClass().getResourceAsStream(handCards.get(i).getImgFileName()))));
        }
    }

    public void updateHandCardImage(int pos, ArrayList<Card> handCards) {
        this.handCardsImages.get(pos).imageProperty().set(new Image(Objects.requireNonNull(getClass().getResourceAsStream(handCards.get(pos).getImgFileName()))));
    }

    public void updateToEmptyCardImage(int pos) {
        this.handCardsImages.get(pos).imageProperty().set(CARDS_EMPTY_CARD_IMAGE);
    }

    public Card dealOneNewCardFromDeck() {
        if (this.cardDeck.size() != 0) {
            Card card = this.cardDeck.remove(0);
            // update deck-size
            this.cardsProperty.set(Integer.toString(this.cardDeck.size()));
            return card;
        } else {
            return null;
        }
    }

    public void addNewHandCard(boolean duckyWin, int pos, ArrayList<Card> handCards) {
        handCards.remove(pos);
        handCards.add(pos, duckyWin ? this.dealOneNewCardFromDeck() : new Card(this.none, 0, GameConfig.CARDS_EMPTY_CARD_FILENAME));
    }
}
