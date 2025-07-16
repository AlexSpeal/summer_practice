package ru.summerpractice.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.summerpractice.enums.Difficulty;
import ru.summerpractice.model.implementation.RandomNumberGenerator;
import ru.summerpractice.model.interfaces.IOServiceInterface;
import ru.summerpractice.model.interfaces.NumberGeneratorInterface;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameSessionTest {
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;
    private ByteArrayOutputStream testOut;

    @BeforeEach
    void setUpStreams() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @AfterEach
    void restoreStreams() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    private static class StubIO implements IOServiceInterface {
        private final Scanner scanner = new Scanner(System.in);

        @Override
        public String readString(String prompt) {
            return "";
        }

        @Override
        public int readInt(String prompt, int min, int max) {
            return scanner.nextInt();
        }

        @Override
        public boolean readBoolean(String prompt) {
            return false;
        }

        @Override
        public void print(String message) {
            System.out.print(message);
        }

        @Override
        public void println(String message) {
            System.out.println(message);
        }
    }


    @Test
    void correctOnFirstTry() {
        NumberGeneratorInterface gen = new RandomNumberGenerator(12345L);
        int secret = gen.generate(Difficulty.EASY.getMin(), Difficulty.EASY.getMax());
        System.setIn(new ByteArrayInputStream((secret + "\n").getBytes()));

        Player player = new Player("Tester");
        IOServiceInterface io = new StubIO();
        new GameSession(player, Difficulty.EASY, new RandomNumberGenerator(12345L), io).start();

        assertEquals(1, player.getGamesPlayed(), "Ожидали 1 сыгранную партию");
        String output = testOut.toString();
        assertTrue(output.contains("угадали за 1 попыт"),
                () -> "Ожидали сообщение об отгадке за 1 попытку, получили:\n" + output);
    }

    @Test
    void exhaustAttempts() {
        Difficulty hard = Difficulty.HARD;
        NumberGeneratorInterface gen = new RandomNumberGenerator(1L);
        int min = hard.getMin();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hard.getMaxAttempts(); i++) {
            sb.append(min).append("\n");
        }
        System.setIn(new ByteArrayInputStream(sb.toString().getBytes()));

        Player player = new Player("Tester");
        IOServiceInterface io = new StubIO();
        new GameSession(player, hard, new RandomNumberGenerator(1L), io).start();

        String output = testOut.toString();
        assertTrue(output.contains("исчерпали все попытки"),
                () -> "Ожидали текст об исчерпании попыток, получили:\n" + output);
    }
}
