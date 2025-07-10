package ru.summerpractice;

import ru.summerpractice.hepler.ConsoleHelper;
import ru.summerpractice.model.GameSession;
import ru.summerpractice.model.Player;

/*«Угадай число» — игра, в которой компьютер загадывает целое число в заданном диапазоне,
 а задача пользователя — отгадать это число за как можно меньшее количество попыток.
Как играть:
После запуска приложения вы вводите своё имя.
Программа генерирует случайное число в диапазоне 1–100.
Пользователь вводит свою догадку.
После каждой попытки игра подсказывает, больше или меньше число загаданного.
Когда пользователь угадывает, приложение выводит, за сколько попыток ему это удалось.
Игра предлагает сыграть ещё раз или выйти.*/

public class GuessGameApp {
    public static void main(String[] args) {
        String name = ConsoleHelper.readString("Введите своё имя: ");
        Player player = new Player(name);
        boolean playAgain;
        do {
            GameSession session = new GameSession(player);
            session.start();
            playAgain = ConsoleHelper.readBoolean("Сыграть ещё раз? (y/n): ");
        } while (playAgain);
        System.out.println("Спасибо за игру, " + player.getName() +
                "! Вы сыграли " + player.getGamesPlayed() + " раз.");
    }
}