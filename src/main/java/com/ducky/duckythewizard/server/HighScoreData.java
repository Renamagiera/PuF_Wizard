package com.ducky.duckythewizard.server;

// data structure for RequestBody when adding new score
public class HighScoreData {

    private int score;
    private String name;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}