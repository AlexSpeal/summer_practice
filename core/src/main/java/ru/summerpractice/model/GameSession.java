package ru.summerpractice.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.summerpractice.enums.Difficulty;
import ru.summerpractice.model.interfaces.IOServiceInterface;
import ru.summerpractice.model.interfaces.NumberGeneratorInterface;

public class GameSession {
    private static final Logger log = LogManager.getLogger(GameSession.class);

    private final Difficulty diff;
    private final Player player;
    private final NumberGeneratorInterface generator;
    private final IOServiceInterface io;

    public GameSession(Player player,
                       Difficulty diff,
                       NumberGeneratorInterface generator,
                       IOServiceInterface io) {
        this.player = player;
        this.diff = diff;
        this.generator = generator;
        this.io = io;
    }

    public void start() {
        int secret = generator.generate(diff.getMin(), diff.getMax());
        log.debug("Сессия для {} сгенерировала число {}", player.getName(), secret);

        io.println("\nЯ загадал число от " + diff.getMin() +
                " до " + diff.getMax() +
                (diff.getMaxAttempts() < Integer.MAX_VALUE
                        ? " (лимит " + diff.getMaxAttempts() + " попыток)" : "") +
                ". Попробуйте угадать!");

        int attempts = 0;
        long startTime = System.currentTimeMillis();

        while (attempts < diff.getMaxAttempts()) {
            attempts++;
            int guess = io.readInt("Попытка " + attempts + ": ",
                    diff.getMin(), diff.getMax());
            log.debug("Попытка #{}: введено {}", attempts, guess);

            if (guess == secret) {
                long timeTaken = System.currentTimeMillis() - startTime;
                log.info("Игрок {} угадал число {} за {} попыток ({} ms)",
                        player.getName(), secret, attempts, timeTaken);
                io.println("Всё верно! Вы угадали за " +
                        attempts + " попыток и " +
                        (timeTaken / 1000.0) + " сек.");
                player.recordResult(attempts, timeTaken);
                return;
            }

            giveHint(guess, secret);
            if (attempts == diff.getMaxAttempts()) {
                log.warn("Игрок {} исчерпал все попытки; секрет был {}", player.getName(), secret);
                io.println("☹ Вы исчерпали все попытки. Было загаданo: " + secret);
            }
        }
    }

    private void giveHint(int guess, int secret) {
        int delta = Math.abs(guess - secret);
        if (delta <= 5) {
            io.println("Очень горячо!");
        } else if (delta <= 15) {
            io.println("Горячо");
        } else if (delta <= 30) {
            io.println("Тепло");
        } else {
            io.println("Холодно");
        }
        log.trace("Подсказка для {}: delta = {}", guess, delta);
    }
}


