package com.ducky.duckythewizard.server;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy="user", cascade = CascadeType.REMOVE)
    private Set<HighScore> highScores;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
