package ru.summerpractice.model.implementation;

import ru.summerpractice.model.interfaces.NumberGeneratorInterface;

import java.util.Random;

public class RandomNumberGenerator implements NumberGeneratorInterface {
    private final Random random;

    public RandomNumberGenerator() {
        this.random = new Random();
    }

    public RandomNumberGenerator(long seed) {
        this.random = new Random(seed);
    }

    @Override
    public int generate(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
}
