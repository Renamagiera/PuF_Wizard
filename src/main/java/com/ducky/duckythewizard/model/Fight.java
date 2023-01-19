package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.model.card.CardModel;

import java.util.Random;

public class Fight {
    private Stone stoneInFight;
    private CardModel duckyCard;
    private CardModel stoneCard;

    private String trump;
    private final boolean duckyPlaysFirst;

    public Fight() {
        // who starts
        this.duckyPlaysFirst = generateDuckyPlaysFirst();
    }


    public void setStoneCard(CardModel stoneCard) {
        this.stoneCard = stoneCard;
    }

    public void setDuckyCard(CardModel duckyCard) {
        this.duckyCard = duckyCard;
    }

    public void setStoneInFight(Stone stoneInFight) { this.stoneInFight = stoneInFight; }

    public void setTrump(Stone stone) { this.trump =stone.getRandomTrumpColorStone().getName(); }

    public CardModel getDuckyCard() {
        return this.duckyCard;
    }

    public CardModel getStoneCard() {
        return this.stoneCard;
    }

    public Stone getStoneInFight() {
        return this.stoneInFight;
    }

    public boolean getDuckyPlaysFirst() {
        return this.duckyPlaysFirst;
    }

    private boolean generateDuckyPlaysFirst() {
        return new Random().nextBoolean();
    }

    /*This method check's who wins the fight according to the rules set
    by the developer ladies. Depending on who wins, a text object is
    rendered to the fight scene to inform the player if they have lost or won.*/
    public boolean determineWinner() {
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
            //each loss causes loss of 10 points
            score -= 10;
        }

        return score;
    }
}
