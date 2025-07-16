package ru.summerpractice.util;

public class HintService {

    public static String getHint(int guess, int secret) {
        int delta = Math.abs(guess - secret);
        if (delta == 0) return null;
        if (delta <= 5) return "Очень горячо!";
        if (delta <= 15) return "Горячо";
        if (delta <= 30) return "Тепло";
        return "Холодно";
    }
}
