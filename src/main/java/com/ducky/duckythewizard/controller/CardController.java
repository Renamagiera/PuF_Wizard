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

    public Card clickedCard(MouseEvent event) {
        int clickedCardPosition = this.deck.getHandCardPosition(event);
        Card clickedCard = this.handCards.get(clickedCardPosition);
        if (!clickedCard.color().getName().equals("none")) {
            return clickedCard;
        }
        return null;
    }

    public void removeAndRenderCardAfterClick(int clickedCardPosition) {
        this.handCards.add(clickedCardPosition, this.getSession().getCardDeck().removeHandCard(clickedCardPosition, this.handCards, false));
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
                        // render an empty card image first
                        getSession().getCardCtrl().removeAndRenderCardAfterClick(gameCtrl.session.getCardDeck().getHandCardPosition(mouseEvent));
                        // determine Winner: take Card from handCards {add new from deck | add empty}
                        removeAllClickHandlers();
                    }

                }
            };
            clickHandlers.add(cardClicked);
            imgView.addEventFilter(MouseEvent.MOUSE_CLICKED, cardClicked);
        }
        fightScene.getExitLbl().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            gameCtrl.stopFight(fightScene);
        });
    }

    private void removeAllClickHandlers() {
        for (int i = 0; i < clickHandlers.size(); i++){
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
