package com.ducky.duckythewizard.controller;

import com.ducky.duckythewizard.model.Game;
import com.ducky.duckythewizard.model.Stone;
import com.ducky.duckythewizard.model.card.Card;
import com.ducky.duckythewizard.model.card.CardDeck;
import com.ducky.duckythewizard.model.config.GameConfig;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class CardController extends Controller {
    ArrayList<Card> handCards = this.getSession().getPlayer().getHandCards();
    ArrayList<EventHandler<MouseEvent>> clickHandlers = new ArrayList<>();
    CardDeck deck = this.getSession().getCardDeck();
    public CardController (Game game) {
        super(game);
    }

    public Card clickedCard(MouseEvent event) {
        int clickedCardPosition = this.deck.getHandCardPosition(event);
        Card clickedCard = this.handCards.get(clickedCardPosition);
        if (!clickedCard.getColor().getName().equals("none")) {
            return clickedCard;
        }
        return null;
    }

    public void cardInit() {
        this.getSession().getCardDeck().renderAllHandCardImages(this.getSession().getPlayer().getHandCards(), this.getSession().getAnchorPaneCards());
    }

    public void removeCard(int clickedCardPosition) {
        this.handCards.add(clickedCardPosition, this.getSession().getCardDeck().removeHandCard(clickedCardPosition, this.handCards, false));
        this.deck.renderSpecialCard((ImageView) this.getSession().getAnchorPaneCards().getChildren().get(clickedCardPosition), "empty");
    }
    public void addMouseEventHandler() {
        for (Node node : this.getSession().getAnchorPaneCards().getChildren()) {
            ImageView imgView = (ImageView) node;
            EventHandler<MouseEvent> cardClicked = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if(!getSession().getIsRunning()) {
                        Card clickedCard = clickedCard(mouseEvent);
                        if (clickedCard != null) {
                            int clickPosition = getSession().getCardDeck().getHandCardPosition(mouseEvent);
                            getSession().getActiveFight().setPlayerCard(clickedCard);
                            // render an empty card-image at clicked card
                            getSession().getCardCtrl().removeCard(clickPosition);
                            // render clicked card to fight-scene
                            getSession().getCardDeck().renderFightCard(clickedCard, getSession().getFightScene(), "ducky");
                            // determine Winner: take Card from handCards {add new from deck | add empty}   // TODO in fight-class
                            removeAllClickHandlers();
                            // add exit-button after card was clicked
                            getSession().getFightScene().addExitLabel();
                            getSession().getFightScene().getExitLbl().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                                getSession().getGameCtrl().endCollision();
                                resetPlayerFightCard();
                            });
                        }
                    }
                }
            };
            clickHandlers.add(cardClicked);
            imgView.addEventFilter(MouseEvent.MOUSE_CLICKED, cardClicked);
        }
    }

    private void removeAllClickHandlers() {
        for (int i = 0; i < GameConfig.AMOUNT_HAND_CARDS; i++){
            ImageView imgView = (ImageView) this.getSession().getAnchorPaneCards().getChildren().get(i);
            imgView.removeEventFilter(MouseEvent.MOUSE_CLICKED, clickHandlers.get(i));
        }
        clickHandlers.clear();
    }

    private void resetPlayerFightCard() {
        this.deck.renderSpecialCard(this.getSession().getFightScene().getImgViewCardDucky(), "empty");
    }

    public void addCardToStones() {
        for (Stone stone : this.getSession().getStoneArrayList()) {
            stone.setCard(this.deck.dealOneNewCardFromDeck());
        }
    }
}
