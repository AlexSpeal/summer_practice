package ru.summerpractice.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {
    private Player p;

    @BeforeEach
    void init() {
        p = new Player("Test");
    }

    @Test
    void recordResultFirstTime() {
        p.recordResult(7, 1500);
        assertEquals(1, p.getGamesPlayed());
        assertEquals(7, p.getBestAttempts());
        assertEquals(1500, p.getBestTimeMillis());
    }

    @Test
    void recordResultWorseAttemptsNoChange() {
        p.recordResult(5, 2000);
        p.recordResult(6, 1000);
        assertEquals(2, p.getGamesPlayed());
        assertEquals(5, p.getBestAttempts());
        assertEquals(2000, p.getBestTimeMillis());
    }

    @Test
    void recordResultSameAttemptsBetterTime() {
        p.recordResult(5, 2000);
        p.recordResult(5, 1500);
        assertEquals(2, p.getGamesPlayed());
        assertEquals(5, p.getBestAttempts());
        assertEquals(1500, p.getBestTimeMillis());
    }
}