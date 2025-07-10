package ru.summerpractice.model;

public class Player {
    private final String name;
    private int gamesPlayed = 0;
    private int bestAttempts = Integer.MAX_VALUE;
    private long bestTimeMillis = Long.MAX_VALUE;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getBestAttempts() {
        return bestAttempts;
    }

    public long getBestTimeMillis() {
        return bestTimeMillis;
    }

    public void recordResult(int attempts, long timeMillis) {
        gamesPlayed++;
        if (attempts < bestAttempts) {
            bestAttempts = attempts;
            bestTimeMillis = timeMillis;
        } else if (attempts == bestAttempts && timeMillis < bestTimeMillis) {
            bestTimeMillis = timeMillis;
        }
    }
}


