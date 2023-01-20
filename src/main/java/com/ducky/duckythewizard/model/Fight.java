package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.model.card.Card;

import java.util.Random;

/**In this class all objects relevant to the current fight are saved (the stone Ducky is fighting against,
 * which cards have been played in the fight, what colour is trump etc. - all collected facts of the fight
 * are then used to determine who has won.*/

public class Fight {

    private Stone stoneInFight;
    private Card duckyCard;
    private Card stoneCard;

    private String trump;
    private final boolean duckyPlaysFirst;

    public Fight() {
        // determine who starts
        this.duckyPlaysFirst = generateDuckyPlaysFirst();
    }


    public void setStoneCard(Card stoneCard) { this.stoneCard = stoneCard; }

    public void setDuckyCard(Card duckyCard) { this.duckyCard = duckyCard; }

    public void setStoneInFight(Stone stoneInFight) { this.stoneInFight = stoneInFight; }

    public void setTrump(Stone stone) { this.trump = stone.getRandomTrumpColorStone().getName(); }

    public Card getDuckyCard() { return this.duckyCard; }

    public Card getStoneCard() { return this.stoneCard; }

    public Stone getStoneInFight() { return this.stoneInFight; }

    public boolean getDuckyPlaysFirst() { return this.duckyPlaysFirst; }

    private boolean generateDuckyPlaysFirst() { return new Random().nextBoolean(); }

    /**This method check's if Ducky wins the fight according to the rules set by the developer ladies.
     * The boolean result is used elsewhere to determine which screen to show to the player (win/loss of fight).*/
    public boolean determineIfDuckyIsWinner() {
        int stoneScore = stoneCard.getValue();
        int duckyScore = duckyCard.getValue();

        // if both got wizard, first wizard wins
        if (duckyScore == 199 && stoneScore == 199) {
            if (this.duckyPlaysFirst) {
                duckyScore += 1;
            } else
                stoneScore += 1;
        } else {
            // if no wizard's was played, check if someone has trump-color:
            if (this.duckyCard.getColor().getName().equals(this.trump)) {
                duckyScore += 100;
            }
            if (this.stoneCard.getColor().getName().equals(this.trump)) {
                stoneScore += 100;
            }
            // if no one had trump color:
            if ((!this.duckyCard.getColor().getName().equals(trump)) && (!this.stoneCard.getColor().getName().equals(trump))) {
                // the one, that started gets +100
                if (this.duckyPlaysFirst) {
                    duckyScore += 100;
                    if (this.stoneCard.getColor().getName().equals(this.duckyCard.getColor().getName())) {
                        // if stone has same color, gets also +100
                        stoneScore += 100;
                    }
                } else {
                    stoneScore += 100;
                    if (this.duckyCard.getColor().getName().equals(this.stoneCard.getColor().getName())) {
                        // if ducky has same color, gets also +100
                        duckyScore += 100;
                    }
                }
            }
        }
        // determining who has higher score and triggering message
        return duckyScore > stoneScore;
    }

    /**This method calculates how many points Ducky has earned according to if he won and with what card in what setting.
     *
     * @param duckyWin informs if Ducky has won the fight*/
    public int calculateScore(boolean duckyWin) {
        int score = 0;

        if (duckyWin) {
            //each win earns 20 points
            score += 20;
            //System.out.println("WIN! Score Calc Start: " + score);

            int duckyCardValue = this.duckyCard.getValue() + 1;

            if (this.duckyPlaysFirst) {
                //if you've won without knowing the stone's card you get extra points
                score += 5;
                //System.out.println("Won without seeing stone card!: score is now: " + score);
            }

            if (this.trump.equals("none")) {
                //winning a no trump round earns extra points
                score += 15;
                //System.out.println("won a no trump round!: score is now: " + score);
            }

            //if you've won without a wizard or trump, you can earn more points the closer your card value is to the stone card value
            if ((this.duckyCard.getValue() != 199) && (!this.duckyCard.getColor().getName().equals(this.trump))) {
                int difference = this.duckyCard.getValue() - this.stoneCard.getValue();
                //System.out.println("Difference is " + difference);

                if (difference < 4) {
                    score += duckyCardValue * 2;
                    //System.out.println("Difference is smaller than 4: score is now " + score);
                }
                else {
                    if (difference < duckyCardValue) {
                        score += duckyCardValue - difference;
                        //System.out.println("Difference is bigger than 4: score is now: " + score);
                    }
                }
            }

        }
        else {
            //each lost fight causes loss of 10 points
            score -= 10;
        }

        return score;
    }
}
