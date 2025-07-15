package ru.summerpractice.model.interfaces;

public interface IOServiceInterface {
    String readString(String prompt);

    int readInt(String prompt, int min, int max);

    boolean readBoolean(String prompt);

    void print(String message);

    void println(String message);
}
