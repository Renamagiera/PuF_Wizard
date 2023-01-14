package com.ducky.duckythewizard.server;

import jakarta.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
public class HighScore {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private int score;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }
}