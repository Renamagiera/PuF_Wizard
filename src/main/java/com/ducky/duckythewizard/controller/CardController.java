package com.ducky.duckythewizard.controller;

import com.ducky.duckythewizard.model.card.Card;
import com.ducky.duckythewizard.model.card.CardDeck;
import com.ducky.duckythewizard.model.Game;
import com.ducky.duckythewizard.model.Stone;
import com.ducky.duckythewizard.model.config.GameConfig;
import com.ducky.duckythewizard.view.FightScene;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

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

    public void cardInit(AnchorPane emptyCardSlots) {
        this.getSession().setAnchorPaneCards(emptyCardSlots);
        this.getSession().getCardDeck().renderAllHandCardImages(this.getSession().getPlayer().getHandCards(), this.getSession().getAnchorPaneCards());
    }

    public void removeCard(int clickedCardPosition) {
        this.handCards.add(clickedCardPosition, this.getSession().getCardDeck().removeHandCard(clickedCardPosition, this.handCards, false));
        this.deck.renderSpecialCard((ImageView) this.getSession().getAnchorPaneCards().getChildren().get(clickedCardPosition), "empty");
    }
    public void addMouseEventHandler(GameController gameCtrl, FightScene fightScene) {
        for (Node node : this.getSession().getAnchorPaneCards().getChildren()) {
            ImageView imgView = (ImageView) node;
            EventHandler<MouseEvent> cardClicked = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if(!gameCtrl.session.getIsRunning()) {
                        Card clickedCard = clickedCard(mouseEvent);
                        if (clickedCard != null) {
                            int clickPosition = gameCtrl.session.getCardDeck().getHandCardPosition(mouseEvent);
                            ArrayList<Card> handCards = gameCtrl.session.getPlayer().getHandCards();
                            AnchorPane anchorPaneCards = gameCtrl.session.getAnchorPaneCards();
                            gameCtrl.session.getActiveFight().setPlayerCard(clickedCard);
                            // render an empty card image first
                            gameCtrl.session.getCardCtrl().removeCard(clickPosition);
                            // render clicked card to fight-scene
                            gameCtrl.session.getCardDeck().renderFightCard(clickedCard, fightScene, "ducky");
                            // determine Winner: take Card from handCards {add new from deck | add empty}
                            removeAllClickHandlers();
                        }
                    }
                }
            };
            clickHandlers.add(cardClicked);
            imgView.addEventFilter(MouseEvent.MOUSE_CLICKED, cardClicked);
        }
        fightScene.getExitLbl().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            gameCtrl.stopFight(fightScene);
            resetPlayerFightCard(fightScene);
        });
    }

    private void removeAllClickHandlers() {
        System.out.println("clickHandler size: " + clickHandlers.size());
        for (int i = 0; i < GameConfig.AMOUNT_HAND_CARDS; i++){
            ImageView imgView = (ImageView) this.getSession().getAnchorPaneCards().getChildren().get(i);
            imgView.removeEventFilter(MouseEvent.MOUSE_CLICKED, clickHandlers.get(i));
        }
        clickHandlers.clear();
    }

    private void resetPlayerFightCard(FightScene fightScene) {
        this.deck.renderSpecialCard(fightScene.getImgViewCardDucky(), "empty");
    }

    public void addCardToStones() {
        for (Stone stone : this.getSession().getStoneArrayList()) {
            stone.setCard(this.deck.dealOneNewCardFromDeck());
        }
    }
}
