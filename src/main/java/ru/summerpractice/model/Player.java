package ru.summerpractice.model;

public class Player {
    private final String name;
    private int gamesPlayed = 0;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void incrementGamesPlayed() {
        gamesPlayed++;
    }
}

