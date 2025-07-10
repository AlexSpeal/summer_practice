package ru.summerpractice.enums;

public enum Difficulty {
    EASY(1, 50, Integer.MAX_VALUE),
    MEDIUM(1, 100, 10),
    HARD(1, 200, 5);

    private final int min;
    private final int max;
    private final int maxAttempts;

    Difficulty(int min, int max, int maxAttempts) {
        this.min = min;
        this.max = max;
        this.maxAttempts = maxAttempts;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    @Override
    public String toString() {
        switch (this) {
            case EASY:
                return "Лёгкий (1–50, без лимита попыток)";
            case MEDIUM:
                return "Средний (1–100, 10 попыток)";
            case HARD:
                return "Сложный (1–200, 5 попыток)";
            default:
                return name();
        }
    }
}

