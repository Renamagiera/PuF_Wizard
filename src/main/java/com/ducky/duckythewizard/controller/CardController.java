package com.ducky.duckythewizard.controller;

import com.ducky.duckythewizard.model.Card;
import com.ducky.duckythewizard.model.CardDeck;
import com.ducky.duckythewizard.model.Game;
import com.ducky.duckythewizard.model.config.GameConfig;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class CardController extends Controller {
    public CardController(Game game) {
        super(game);
    }

    public void cardClicked(MouseEvent event) {

        //GameConfig gameConfig = this.getGameConfig();


        int handCardPosition = GameConfig.CARD_SLOT_POSITION.get(((ImageView)event.getSource()).getId());
        Card clickedCard = GameConfig.handedCards.get(handCardPosition);
        if (clickedCard.color().getName().equals("none")) {
            GameConfig.handedCards.remove(handCardPosition);
            GameConfig.handedCards.add(handCardPosition, GameConfig.deckObject.dealNewCardFromDeck());
            GameConfig.deckObject.renderHandCardImages(GameConfig.anchorPaneCards);
        } else {
            GameConfig.deckObject.removeCardAddDummy(GameConfig.CARD_SLOT_POSITION.get(((ImageView)event.getSource()).getId()));
        }
    }
}
