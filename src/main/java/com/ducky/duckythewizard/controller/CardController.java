package com.ducky.duckythewizard.controller;

import com.ducky.duckythewizard.model.*;
import javafx.scene.Node;
import javafx.scene.chart.BubbleChart;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class CardController extends Controller {
    ArrayList<Card> handCards = this.getSession().getPlayer().getHandCards();
    CardDeck deck = this.getSession().getCardDeck();
    public CardController(Game game) {
        super(game);
    }

    public void cardClicked(MouseEvent event) {
        NewNode testNode = new NewNode(this.getSession().getRootAnchorPane());

        AnchorPane fightScene = new AnchorPane();
        fightScene.setId("fightScene");
        fightScene.setLayoutX(200.00);
        fightScene.setLayoutY(130.00);
        fightScene.setPrefHeight(300.00);
        fightScene.setPrefWidth(400.00);
        fightScene.setStyle("-fx-background-color: #001b30");
        //fightScene.getStylesheets().add("/com/ducky/duckythewizard/styles/styleRenate.css");
        this.getSession().getRootAnchorPane().getChildren().add(fightScene);

        // Test für Text-Erstellung und random-Number Generierung
/*        TextObject addThisTextPls = new TextObject("Hallo Sie haben geklickt", "red", "40px", this.getSession().getGameColorObject());
        addThisTextPls.addTextToNodeCenterX(this.getSession().getRootAnchorPane(), 100);
        System.out.println(this.getSession().getGameColorObject().getRandomTrumpColor());*/

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

    public void addCardToStones() {
        for (Stone stone : this.getSession().getStoneArrayList()) {
            stone.setCard(this.deck.dealOneNewCardFromDeck());
        }
    }
}
