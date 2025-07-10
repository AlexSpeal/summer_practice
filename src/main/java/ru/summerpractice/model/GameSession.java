package ru.summerpractice.model;

import ru.summerpractice.hepler.ConsoleHelper;

import java.util.Random;

public class GameSession {
    private static final int MIN = 1;
    private static final int MAX = 100;

    private final int secretNumber;
    private final Player player;
    private int attempts = 0;

    public GameSession(Player player) {
        this.player = player;
        this.secretNumber = new Random().nextInt(MAX - MIN + 1) + MIN;
    }

    public void start() {
        System.out.println("Я загадал число от " + MIN + " до " + MAX +
                ". Попробуйте угадать!");
        while (true) {
            int guess = ConsoleHelper.readInt("Ваш ответ: ", MIN, MAX);
            attempts++;
            if (guess < secretNumber) {
                System.out.println("Загаданное число больше.");
            } else if (guess > secretNumber) {
                System.out.println("Загаданное число меньше.");
            } else {
                System.out.println("Верно! Вы угадали за " + attempts + " попыток.");
                player.incrementGamesPlayed();
                break;
            }
        }
    }
}

