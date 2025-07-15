package ru.summerpractice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger log = LogManager.getLogger(GuessGameApp.class);

    public static void main(String[] args) {
        IOServiceInterface io = new ConsoleHelper();
        NumberGeneratorInterface gen = new RandomNumberGenerator();

        log.info("=== Приложение «Угадай число» запущено ===");
        io.println("=== Добро пожаловать в «Угадай число»! ===");

        String name = io.readString("Введите своё имя: ");
        log.info("Игрок вошёл под именем: {}", name);

        Player player = new Player(name);

        do {
            Difficulty diff = chooseDifficulty(io);
            log.info("Игрок {} выбрал сложность: {}", name, diff);

            GameSession session = new GameSession(player, diff, gen, io);
            session.start();

            io.println("\nСтатистика " + player.getName() + ":");
            io.println("  Сыграно партий: " + player.getGamesPlayed());
            if (player.getBestAttempts() < Integer.MAX_VALUE) {
                io.print("  Лучший результат: " + player.getBestAttempts());
                io.println(" попыток за " +
                        String.format("%.2f", player.getBestTimeMillis() / 1000.0) + " сек.");
            }

        } while (io.readBoolean("\nСыграть ещё? (y/n): "));

        log.info("Игрок {} вышел из приложения. Всего сыграно партий: {}",
                name, player.getGamesPlayed());
        io.println("Спасибо за игру, " + player.getName() + "! До новых встреч.");
        log.info("=== Приложение завершило работу ===");
    }

    private static Difficulty chooseDifficulty(IOServiceInterface io) {
        io.println("Выберите уровень сложности:");
        for (Difficulty d : Difficulty.values()) {
            io.println("  " + (d.ordinal() + 1) + ". " + d);
        }
        return Difficulty.values()[io.readInt(
                "Ваш выбор (1–" + Difficulty.values().length + "): ",
                1, Difficulty.values().length) - 1];
    }
}
