package com.ducky.duckythewizard.model;

// data model for high score tableview
public class HighScore {

    private int rank;
    private String name;
    private int score;

    public HighScore(int rank, String name, int score) {
        this.rank = rank;
        this.name = name;
        this.score = score;
    }

    public int getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
