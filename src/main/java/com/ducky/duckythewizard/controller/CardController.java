package com.ducky.duckythewizard.controller;

import com.ducky.duckythewizard.model.*;
import com.ducky.duckythewizard.view.FightScene;
import javafx.scene.Node;
import javafx.scene.chart.BubbleChart;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;

public class CardController extends Controller {
    ArrayList<Card> handCards = this.getSession().getPlayer().getHandCards();
    CardDeck deck = this.getSession().getCardDeck();
    public CardController (Game game) {
        super(game);
    }

    public void cardClicked(MouseEvent event) {
        // Test für Text-Erstellung und random-Number Generierung
/*        TextObject addThisTextPls = new TextObject("Hallo Sie haben geklickt", "red", "40px", this.getSession().getGameColorObject());
        addThisTextPls.addTextToNodeCenterX(this.getSession().getRootAnchorPane(), 100);
        System.out.println(this.getSession().getGameColorObject().getRandomTrumpColor());*/

        // nur klick-bar, wenn game-session nicht läuft (isRunning = false)
        if (!this.getSession().getIsRunning()) {
            // die geklickte Karte eventuell als Game-Variable anlegen
            int handCardClickedPosition = this.deck.getHandCardPosition(event);
            Card clickedCard = this.handCards.get(handCardClickedPosition);

            if (clickedCard.color().getName().equals("none")) {
                this.handCards.add(handCardClickedPosition, this.getSession().getCardDeck().removeHandCard(handCardClickedPosition, this.handCards, false));
            } else {
                this.handCards.add(handCardClickedPosition, this.getSession().getCardDeck().removeHandCard(handCardClickedPosition, this.handCards, true));
            }
            this.deck.renderOneCard(handCardClickedPosition, handCards, this.getSession().getAnchorPaneCards());
        } else {
            System.out.println("not clickable");
        }
    }

    public Card clickedCard(MouseEvent event) {
        int clickedCardPosition = this.deck.getHandCardPosition(event);
        Card clickedCard = this.handCards.get(clickedCardPosition);
        if (!clickedCard.color().getName().equals("none")) {
            return clickedCard;
        }
        return null;
    }

    public void removeAndRenderCardAfterClick(int clickedCardPosition) {
        this.handCards.add(clickedCardPosition, this.getSession().getCardDeck().removeHandCard(clickedCardPosition, this.handCards, true));
        this.deck.renderOneCard(clickedCardPosition, this.handCards, this.getSession().getAnchorPaneCards());
    }

    public void addMouseEventHandler(GameController gameCtrl, FightScene fightScene) {
        System.out.println("add mouse event handler: " + this.getSession().getIsRunning());
        for (Node node : this.getSession().getAnchorPaneCards().getChildren()) {
            ImageView imgView = (ImageView) node;
            imgView.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                // set Player-card
                this.getSession().getActiveFight().setPlayerCard(clickedCard(mouseEvent));
                this.getSession().getCardCtrl().removeAndRenderCardAfterClick(gameCtrl.session.getCardDeck().getHandCardPosition(mouseEvent));
                // determine Winner: take Card from handCards {add new from deck | add empty}
                gameCtrl.stopFight(fightScene);
            });
        }
    }

    public void addCardToStones() {
        for (Stone stone : this.getSession().getStoneArrayList()) {
            stone.setCard(this.deck.dealOneNewCardFromDeck());
        }
    }
}
