package ru.summerpractice.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class HintServiceTest {

    @Test
    void noHintWhenEqual() {
        assertNull(HintService.getHint(7, 7));
    }

    @Test
    void veryHotWhenWithin5() {
        assertEquals("Очень горячо!", HintService.getHint(10, 14));
        assertEquals("Очень горячо!", HintService.getHint(15, 10));
    }

    @Test
    void hotWhenWithin15() {
        assertEquals("Горячо", HintService.getHint(10, 24));
    }

    @Test
    void warmWhenWithin30() {
        assertEquals("Тепло", HintService.getHint(50, 20));
    }

    @Test
    void coldWhenBeyond30() {
        assertEquals("Холодно", HintService.getHint(1, 100));
    }
}