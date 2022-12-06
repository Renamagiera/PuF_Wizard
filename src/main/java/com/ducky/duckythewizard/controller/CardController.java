package com.ducky.duckythewizard.controller;

import com.ducky.duckythewizard.model.*;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class CardController extends Controller {
    ArrayList<Card> handCards = this.getSession().getPlayer().getHandCards();
    CardDeck deck = this.getSession().getCardDeck();
    public CardController(Game game) {
        super(game);
    }

    public void cardClicked(MouseEvent event) {
        // Test für Text-Erstellung und random-Number Generierung
/*        TextObject addThisTextPls = new TextObject("Hallo Sie haben geklickt", "red", "40px");
        addThisTextPls.addTextToNodeCenterX(this.getSession().getRootAnchorPane(), 100);
        System.out.println(this.getSession().getGameColorObject().getTrumpColorObject().getRandomTrumpColor());*/

        // die geklickte Karte eventuell als Game-Variable anlegen, wenn du sie irgendwo anders brauchst
        int handCardClickedPosition = this.deck.getHandCardPosition(event);
        Card clickedCard = this.handCards.get(handCardClickedPosition);

        if (clickedCard.color().getName().equals("none")) {
            this.handCards.add(handCardClickedPosition, this.getSession().getCardDeck().removeHandCard(handCardClickedPosition, this.handCards, false));
        } else {
            this.handCards.add(handCardClickedPosition, this.getSession().getCardDeck().removeHandCard(handCardClickedPosition, this.handCards, true));
        }
        this.deck.renderOneCard(handCardClickedPosition, handCards, this.getSession().getAnchorPaneCards());
    }
}
