package com.ducky.duckythewizard.controller;

import com.ducky.duckythewizard.model.Card;
import com.ducky.duckythewizard.model.CardDeck;
import com.ducky.duckythewizard.model.Game;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class CardController extends Controller {
    ArrayList<Card> handCards = this.getSession().getHandCards();
    CardDeck deck = this.getSession().getCardDeck();
    public CardController(Game game) {
        super(game);
    }

    public void cardClicked(MouseEvent event) {
        int handCardClickedPosition = this.deck.getHandCardPosition(event);
        Card clickedCard = this.handCards.get(handCardClickedPosition);
        if (clickedCard.color().getName().equals("none")) {
            this.handCards.add(handCardClickedPosition, this.getSession().getCardDeck().removeHandCard(handCardClickedPosition, this.handCards, false));
            this.deck.renderOneCard(handCardClickedPosition, handCards, this.getSession().getAnchorPaneCards());
        } else {
            this.handCards.add(handCardClickedPosition, this.getSession().getCardDeck().removeHandCard(handCardClickedPosition, this.getSession().getHandCards(), true));
            this.deck.renderOneCard(handCardClickedPosition, handCards, this.getSession().getAnchorPaneCards());
        }
    }
}
