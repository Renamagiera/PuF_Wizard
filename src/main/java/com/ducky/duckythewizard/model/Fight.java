package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.model.card.Card;
import com.ducky.duckythewizard.model.color.GameColor;
import com.ducky.duckythewizard.model.color.GameColorObject;

import java.util.Random;

public class Fight {
    private Stone stoneInFight;
    private Card duckyCard;
    private Card stoneCard;
    private boolean duckyPlaysFirst;

    public Fight(GameColorObject gameColorObject) {
        // who starts
        this.duckyPlaysFirst = generateDuckyPlaysFirst();
    }

    public Card getDuckyCard() {
        return duckyCard;
    }

    public Card getStoneCard() {
        return stoneCard;
    }

    public void setStoneCard(Card stoneCard) {
        this.stoneCard = stoneCard;
    }

    public void setDuckyCard(Card duckyCard) {
        this.duckyCard = duckyCard;
    }

    public void setStoneInFight(Stone stoneInFight) {
        this.stoneInFight = stoneInFight;
    }

    public Stone getStoneInFight() {
        return stoneInFight;
    }

    public boolean getDuckyPlaysFirst() {
        return this.duckyPlaysFirst;
    }

    private boolean generateDuckyPlaysFirst() {
        return new Random().nextBoolean();
    }

    /*This method check's who wins the fight according to the rules set
    by the developer ladies. Depending on who wins, a text object is
    rendered to the fight scene to inform the player if they lost or won.*/
    public boolean determineWinner() {
        int stoneScore = stoneCard.getValue();
        int duckyScore = duckyCard.getValue();
        String trump = this.stoneInFight.getRandomTrumpColorStone().getName();

        // if both got wizard, first wizard wins
        // no need to check more?
        if (duckyScore == 199 && stoneScore == 199) {
            if (this.duckyPlaysFirst) {
                duckyScore += 1;
            } else
                stoneScore += 1;
        } else {
            // if no wizard's was played, check if someone has trump-color:
            if (duckyCard.getColor().getName().equals(trump)) {
                duckyScore += 100;
            }
            if (stoneCard.getColor().getName().equals(trump)) {
                stoneScore += 100;
            }
            // if no one had trump color:
            if ((!duckyCard.getColor().getName().equals(trump)) && (!stoneCard.getColor().getName().equals(trump))) {
                // the one, that started gets +100
                if (this.duckyPlaysFirst) {
                    duckyScore += 100;
                    if (stoneCard.getColor().getName().equals(duckyCard.getColor().getName())) {
                        // if stone has same color, gets also +100
                        stoneScore += 100;
                    }
                } else {
                    stoneScore += 100;
                    if (duckyCard.getColor().getName().equals(stoneCard.getColor().getName())) {
                        // if ducky has same color, gets also +100
                        duckyScore += 100;
                    }
                }
            }
        }
        // determining who has higher score and triggering message
        return duckyScore > stoneScore;
    }
}
