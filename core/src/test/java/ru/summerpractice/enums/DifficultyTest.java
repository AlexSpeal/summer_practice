package ru.summerpractice.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DifficultyTest {

    @Test
    void toStringFormatsCorrectly() {
        assertEquals("Лёгкий (1–50, без лимита попыток)", Difficulty.EASY.toString());
        assertEquals("Средний (1–100, 10 попыток)",       Difficulty.MEDIUM.toString());
        assertEquals("Сложный (1–200, 5 попыток)",      Difficulty.HARD.toString());
    }
}