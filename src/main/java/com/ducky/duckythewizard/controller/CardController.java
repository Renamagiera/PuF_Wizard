package com.ducky.duckythewizard.controller;

import com.ducky.duckythewizard.model.Card;
import com.ducky.duckythewizard.model.CardDeck;
import com.ducky.duckythewizard.model.Game;
import com.ducky.duckythewizard.model.Stone;
import com.ducky.duckythewizard.view.FightScene;
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

/*    public Card cardClicked(MouseEvent event) {
 *//*       // Test für Text-Erstellung und random-Number Generierung
        TextObject addThisTextPls = new TextObject("Hallo Sie haben geklickt", "red", "40px", this.getSession().getGameColorObject());
        addThisTextPls.addTextToNodeCenterX(this.getSession().getRootAnchorPane(), 100);
        System.out.println(this.getSession().getGameColorObject().getRandomTrumpColor());*//*

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
        return null;
    }*/

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
        for (Node node : this.getSession().getAnchorPaneCards().getChildren()) {
            ImageView imgView = (ImageView) node;
            EventHandler<MouseEvent> cardClicked = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if(!getSession().getIsRunning()) {
                        getSession().getActiveFight().setPlayerCard(clickedCard(mouseEvent));
                        getSession().getCardCtrl().removeAndRenderCardAfterClick(gameCtrl.session.getCardDeck().getHandCardPosition(mouseEvent));
                        // determine Winner: take Card from handCards {add new from deck | add empty}
                        gameCtrl.stopFight(fightScene);
                        removeAllClickHandlers();
                    }

                }
            };
            clickHandlers.add(cardClicked);
            imgView.addEventFilter(MouseEvent.MOUSE_CLICKED, cardClicked);
        }
    }

    private void removeAllClickHandlers() {
        for (int i = 0; i < this.getSession().getAnchorPaneCards().getChildren().size(); i++){
            ImageView imgView = (ImageView) this.getSession().getAnchorPaneCards().getChildren().get(i);
            imgView.removeEventFilter(MouseEvent.MOUSE_CLICKED, clickHandlers.get(i));
        }
        clickHandlers.clear();
    }

    public void addCardToStones() {
        for (Stone stone : this.getSession().getStoneArrayList()) {
            stone.setCard(this.deck.dealOneNewCardFromDeck());
        }
    }
}
