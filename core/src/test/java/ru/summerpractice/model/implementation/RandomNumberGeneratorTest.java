package ru.summerpractice.model.implementation;

import org.junit.jupiter.api.Test;
import ru.summerpractice.model.interfaces.NumberGeneratorInterface;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RandomNumberGeneratorTest {

    @Test
    void generateWithinBounds() {
        NumberGeneratorInterface gen = new RandomNumberGenerator(12345L);
        int v = gen.generate(10, 20);
        assertTrue(v >= 10 && v <= 20);
        assertEquals((new RandomNumberGenerator(12345L)).generate(10, 20), v);
    }

    @Test
    void generateMinEqualsMax() {
        NumberGeneratorInterface gen = new RandomNumberGenerator(42L);
        assertEquals(5, gen.generate(5, 5));
    }
}