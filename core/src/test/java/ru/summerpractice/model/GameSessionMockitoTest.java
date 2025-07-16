
package ru.summerpractice.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.summerpractice.enums.Difficulty;
import ru.summerpractice.model.interfaces.IOServiceInterface;
import ru.summerpractice.model.interfaces.NumberGeneratorInterface;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameSessionMockitoTest {

    @Mock
    NumberGeneratorInterface generator;

    @Mock
    IOServiceInterface io;

    @Test
    void testStartSuccessCalls() {
        int secret = 42;
        when(generator.generate(Difficulty.EASY.getMin(), Difficulty.EASY.getMax()))
                .thenReturn(secret);
        when(io.readInt("Попытка 1: ", Difficulty.EASY.getMin(), Difficulty.EASY.getMax()))
                .thenReturn(secret);

        Player player = new Player("Tester");
        GameSession session = new GameSession(player, Difficulty.EASY, generator, io);
        session.start();

        verify(generator, times(1))
                .generate(Difficulty.EASY.getMin(), Difficulty.EASY.getMax());
        verify(io, times(1))
                .println(contains("Я загадал число от"));
        verify(io, times(1))
                .readInt("Попытка 1: ", Difficulty.EASY.getMin(), Difficulty.EASY.getMax());
        verify(io, times(1))
                .println(contains("Всё верно! Вы угадали за 1 попыток"));
        assertEquals(1, player.getGamesPlayed());
    }

    @Test
    void testStartWithHintAndThenSuccess() {
        int secret = 50;
        when(generator.generate(anyInt(), anyInt())).thenReturn(secret);
        when(io.readInt(eq("Попытка 1: "), anyInt(), anyInt()))
                .thenReturn(secret - 20);
        when(io.readInt(eq("Попытка 2: "), anyInt(), anyInt()))
                .thenReturn(secret);

        Player player = new Player("Tester");
        GameSession session = new GameSession(player, Difficulty.EASY, generator, io);

        session.start();

        verify(io).println("Тепло");
        verify(io).println(contains("Всё верно! Вы угадали за 2 попыток"));
        assertEquals(1, player.getGamesPlayed());
    }

    @Test
    void testStartFailure() {
        when(generator.generate(anyInt(), anyInt())).thenReturn(9);
        when(io.readInt(anyString(), anyInt(), anyInt()))
                .thenReturn(1, 1, 1, 1, 1);

        Player player = new Player("Tester");
        GameSession session = new GameSession(player, Difficulty.HARD, generator, io);

        session.start();

        verify(io, times(Difficulty.HARD.getMaxAttempts()))
                .readInt(anyString(), eq(Difficulty.HARD.getMin()), eq(Difficulty.HARD.getMax()));
        verify(io, times(1))
                .println(contains("☹ Вы исчерпали все попытки"));
        assertEquals(1, player.getGamesPlayed());
    }
}
