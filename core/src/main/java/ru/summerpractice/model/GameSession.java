package ru.summerpractice.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.summerpractice.enums.Difficulty;
import ru.summerpractice.model.interfaces.IOServiceInterface;
import ru.summerpractice.model.interfaces.NumberGeneratorInterface;
import ru.summerpractice.util.HintService;

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

        printIntroduction();
        runGameLoop(secret);
    }

    private void printIntroduction() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nЯ загадал число от ")
                .append(diff.getMin())
                .append(" до ")
                .append(diff.getMax());
        if (diff.getMaxAttempts() < Integer.MAX_VALUE) {
            sb.append(" (лимит ")
                    .append(diff.getMaxAttempts())
                    .append(" попыток)");
        }
        sb.append(". Попробуйте угадать!");
        io.println(sb.toString());
    }

    private void runGameLoop(int secret) {
        int attempts = 0;
        long startTime = System.currentTimeMillis();

        while (attempts < diff.getMaxAttempts()) {
            attempts++;
            int guess = readGuess(attempts);
            log.debug("Попытка #{}: введено {}", attempts, guess);

            if (guess == secret) {
                handleSuccess(attempts, startTime, secret);
                return;
            }

            handleHint(guess, secret);
            if (attempts == diff.getMaxAttempts()) {
                handleFailure(secret);
            }
        }
    }

    private int readGuess(int attempt) {
        String prompt = String.format("Попытка %d: ", attempt);
        return io.readInt(prompt, diff.getMin(), diff.getMax());
    }

    private void handleSuccess(int attempts, long startTime, int secret) {
        long timeTaken = System.currentTimeMillis() - startTime;
        log.info("Игрок {} угадал число {} за {} попыток ({} ms)",
                player.getName(), secret, attempts, timeTaken);
        io.println(String.format("Всё верно! Вы угадали за %d попыток и %.2f сек.",
                attempts, timeTaken / 1000.0));
        player.recordResult(attempts, timeTaken);
    }

    private void handleHint(int guess, int secret) {
        String hint = HintService.getHint(guess, secret);
        if (hint != null) {
            io.println(hint);
            log.trace("Подсказка: {}", hint);
        }
    }

    private void handleFailure(int secret) {
        log.warn("Игрок {} исчерпал все попытки; секрет был {}", player.getName(), secret);
        io.println("☹ Вы исчерпали все попытки. Было загаданo: " + secret);
    }
}
