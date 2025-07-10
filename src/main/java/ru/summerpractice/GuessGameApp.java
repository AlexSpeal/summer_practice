package ru.summerpractice;

import ru.summerpractice.enums.Difficulty;
import ru.summerpractice.hepler.ConsoleHelper;
import ru.summerpractice.model.GameSession;
import ru.summerpractice.model.Player;
import ru.summerpractice.model.implementation.RandomNumberGenerator;
import ru.summerpractice.model.interfaces.IOServiceInterface;
import ru.summerpractice.model.interfaces.NumberGeneratorInterface;

/*«Угадай число» — игра, в которой компьютер загадывает целое число в заданном диапазоне,
 а задача пользователя — отгадать это число за как можно меньшее количество попыток.
Как играть:
После запуска приложения вы вводите своё имя.
Программа генерирует случайное число в выбранном диапазоне.
Пользователь вводит свою догадку.
После каждой попытки игра подсказывает, больше или меньше число загаданного.
Когда пользователь угадывает, приложение выводит, за сколько попыток ему это удалось.
Игра предлагает сыграть ещё раз или выйти.*/

public class GuessGameApp {
    public static void main(String[] args) {
        IOServiceInterface io = new ConsoleHelper();
        NumberGeneratorInterface gen = new RandomNumberGenerator();

        io.println("=== Добро пожаловать в «Угадай число»! ===");
        String name = io.readString("Введите своё имя: ");
        Player player = new Player(name);

        do {
            Difficulty diff = chooseDifficulty(io);
            new GameSession(player, diff, gen, io).start();

            io.println("\nСтатистика " + player.getName() + ":");
            io.println("  Сыграно партий: " + player.getGamesPlayed());
            if (player.getBestAttempts() < Integer.MAX_VALUE) {
                io.print("  Лучший результат: " + player.getBestAttempts());
                io.println(" попыток за "
                        + String.format("%.2f", player.getBestTimeMillis() / 1000.0) + " сек.");
            }

        } while (io.readBoolean("\nСыграть ещё? (y/n): "));

        io.println("Спасибо за игру, " + player.getName() + "! До новых встреч.");
    }

    private static Difficulty chooseDifficulty(IOServiceInterface io) {
        io.println("Выберите уровень сложности:");
        for (Difficulty d : Difficulty.values()) {
            io.println("  " + (d.ordinal() + 1) + ". " + d);
        }
        int choice = io.readInt("Ваш выбор (1–" + Difficulty.values().length + "): ",
                1, Difficulty.values().length);
        return Difficulty.values()[choice - 1];
    }
}
