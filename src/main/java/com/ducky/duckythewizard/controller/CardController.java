package com.ducky.duckythewizard.controller;

import com.ducky.duckythewizard.model.Game;
import com.ducky.duckythewizard.model.Stone;
import com.ducky.duckythewizard.model.card.Card;
import com.ducky.duckythewizard.model.card.CardDeck;
import com.ducky.duckythewizard.model.config.GameConfig;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class CardController extends Controller {
    private ArrayList<Card> handCards;
    private CardDeck cardDeck;
    private AnchorPane anchorPaneCards;

    private ArrayList<EventHandler<MouseEvent>> clickHandlers = new ArrayList<>();

    public CardController(Game game) {
        super(game);
    }

    public void cardInit() {
        // init card-deck local variables, add cards to deck
        this.getSession().getCardDeckModel(). setTrumpColors(this.getSession().getGameColorObject().getTrumpColors());
        this.getSession().getCardDeckModel().setWizard(this.getSession().getGameColorObject().getTrumpColorObject().getWizard());
        this.getSession().getCardDeckModel().setNone(this.getSession().getGameColorObject().getTrumpColorObject().getNone());
        this.getSession().getCardDeckModel().storeCardsInDeckArray();

        /*Initialize player hand-cards. Render hand-card images. Set local variables from game-session*/
        this.cardDeck = this.getSession().getCardDeckModel();
        // set player's hand-cards
        this.getSession().getPlayer().setHandCards(this.dealHandCards());
        this.handCards = this.getSession().getPlayer().getHandCards();
        this.anchorPaneCards = (AnchorPane) this.getSession().getRootAnchorPane().lookup("#emptyCardSlots");
        // render all hand-card images to card-anchor-pane
        this.getSession().getCardDeckModel().updateAllHandCardImages(this.handCards);
    }

    public ArrayList<Card> dealHandCards() {
        ArrayList<Card> takenCards = new ArrayList<>();
        for (int i = 0; i < GameConfig.CARDS_AMOUNT_HANDCARDS; i++) {
            takenCards.add(this.cardDeck.getCardDeck().remove(0));
        }
        // update deck-size
        this.cardDeck.cardsProperty.set(Integer.toString(this.cardDeck.getCardDeck().size()));
        return takenCards;
    }

    public void addMouseEventHandler() {
        for (Node node : this.anchorPaneCards.getChildren()) {
            ImageView imgView = (ImageView) node;
            EventHandler<MouseEvent> cardClicked = cardMouseClick -> {
                // take focus, so key's can be pressed
                getSession().getFightView().getAnchorPaneFightOverlay().requestFocus();
                if(!getSession().getIsRunning()) {
                    Card clickedCard = determineClickedCard(cardMouseClick);

                    if (clickedCard != null) {
                        // if ducky played first, the stone card needs to be rendered after clicking a card
                        if (getSession().getActiveFight().getDuckyPlaysFirst()) {
                            getSession().getFightView().updateFightViewCardProp(false);
                        }

                        // HANDLE PLAYER'S CLICKED CARD
                        this.handleCardClick(cardMouseClick, clickedCard);

                        // DETERMINE WINNER
                        // set win- or loss-label, add score to player
                        boolean duckyWin = getSession().getActiveFight().determineIfDuckyIsWinner();
                        getSession().getFightView().updateWinLossLabelProp(duckyWin);
                        getSession().getPlayer().addToScore(getSession().getActiveFight().calculateScore(duckyWin));

                        // MAKE NO MORE CARD CLICKABLE
                        this.removeAllClickHandlers();

                        // RENDER END-SCENE
                        String endOfGame = getSession().getGameCtrl().checkEndOfGame(duckyWin);
                        if (endOfGame.equals("win") || endOfGame.equals("loss")) {
                            getSession().getGameCtrl().renderEndScene(endOfGame.equals("win"));
                        }

                        // END FIGHT-SCENE
                        this.getSession().getFightView().exitFightViewVisible.set(true);
                        EventHandler<MouseEvent> exitEvent = new EventHandler<>() {
                            @Override
                            public void handle(MouseEvent event) {
                                getSession().getRootAnchorPane().lookup("#exitFightView").removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
                                endFight(getSession().getCardDeckModel().returnHandCardPosition(cardMouseClick), duckyWin);
                            }
                        };
                        this.getSession().getRootAnchorPane().lookup("#exitFightView").addEventHandler(MouseEvent.MOUSE_CLICKED, exitEvent);
                        // end fight-view clicking ESC
                        getSession().getFightView().getAnchorPaneFightOverlay().setOnKeyPressed(keyEvent -> {
                            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                                this.endFight(getSession().getCardDeckModel().returnHandCardPosition(cardMouseClick), duckyWin);
                            }
                        });
                    }
                }
            };
            this.clickHandlers.add(cardClicked);
            imgView.addEventFilter(MouseEvent.MOUSE_CLICKED, cardClicked);
        }
    }

    public Card determineClickedCard(MouseEvent event) {
        /*determine with card from player's hand-cards was clicked. Return hand-card just if it's not empty*/
        int clickedCardPosition = this.cardDeck.returnHandCardPosition(event);
        Card clickedCard = this.handCards.get(clickedCardPosition);
        return !clickedCard.getColor().getName().equals("none") ? clickedCard : null;
    }

    private void handleCardClick(MouseEvent event, Card clickedCard) {
        /*First set player fight-card in fight-object. Replace an empty card at player's clicked position in hand-cards
        and render it. Render clicked card at fight-scene.*/
        int clickPosition = getSession().getCardDeckModel().returnHandCardPosition(event);
        getSession().getActiveFight().setDuckyCard(clickedCard);
        this.cardDeck.updateToEmptyCardImage(clickPosition);
        getSession().getFightView().updateFightViewCardProp(true);
    }

    private void removeAllClickHandlers() {
        for (int i = 0; i < GameConfig.CARDS_AMOUNT_HANDCARDS; i++){
            ImageView imgView = (ImageView) this.anchorPaneCards.getChildren().get(i);
            imgView.removeEventFilter(MouseEvent.MOUSE_CLICKED, clickHandlers.get(i));
        }
        clickHandlers.clear();
    }

    private void endFight(int pos, boolean duckyWin) {
        /*End Fight. Close Fight-Scene, GameCtrl restarts everything. New cards are dealt from deck.*/
        getSession().getGameCtrl().endCollision();
        this.dealNewCardsFromDeck(pos, duckyWin);
    }

    private void dealNewCardsFromDeck(int pos, boolean duckyWin) {
        /*give ducky one new card. If he won the fight he gets one new card from deck.
        Otherwise, ducky gets an empty card. Update images.*/
        this.cardDeck.addNewHandCard(duckyWin, pos, this.handCards);
        this.cardDeck.updateHandCardImage(pos, this.handCards);
        this.getSession().getActiveFight().getStoneInFight().setCard(this.cardDeck.dealOneNewCardFromDeck());
    }

    public void addCardToStones() {
        for (Stone stone : this.getSession().getStoneArrayList()) {
            stone.setCard(this.cardDeck.dealOneNewCardFromDeck());
        }
    }
}
