package com.ducky.duckythewizard.controller;

import com.ducky.duckythewizard.model.Card;
import com.ducky.duckythewizard.model.CardDeck;
import com.ducky.duckythewizard.model.Game;
import com.ducky.duckythewizard.model.GameConfig;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class CardController extends Controller {
    public CardController(Game game) {
        super(game);
    }

    public void cardClicked(MouseEvent event) {

        GameConfig gameConfig = this.getGameConfig();

        System.out.println("Deck size: " + gameConfig.deck.size());
        // DELETE ME LATER
        //System.out.println("Do some stuff with this card: " + ((ImageView) event.getSource()).getId());
        //System.out.println(event.getSource());

        // für Test-Zwecke, wenn die Karte bereits gespielt wurde, soll eine neue aus dem Deck genommen werden
        int handCardPosition = gameConfig.CARD_SLOT_POSITION.get(((ImageView)event.getSource()).getId());
        Card clickedCard = gameConfig.handedCards.get(handCardPosition);
        CardDeck.showHandCards(gameConfig.handedCards);
        System.out.println("Color clicked card: " + clickedCard.color().getName());
        if (clickedCard.color().getName().equals("none")) {
            System.out.println("this card is already gone, here is a new one");
            gameConfig.handedCards.remove(handCardPosition);
            gameConfig.handedCards.add(handCardPosition, gameConfig.deckObject.dealNewCardFromDeck());
            System.out.println("Deck size: " + gameConfig.deck.size());
            System.out.println("new Card: " + gameConfig.handedCards.get(handCardPosition).color().getName());
            gameConfig.deckObject.renderHandCardImages(gameConfig.anchorPane);
        } else {
            gameConfig.deckObject.removeCardAddDummy(gameConfig.CARD_SLOT_POSITION.get(((ImageView)event.getSource()).getId()));
        }
        // DELETE ME LATER

        //GameConfig.deckObject.removeCardAddDummy(GameConfig.CARD_SLOT_POSITION.get(((ImageView)event.getSource()).getId()));
    }
}
