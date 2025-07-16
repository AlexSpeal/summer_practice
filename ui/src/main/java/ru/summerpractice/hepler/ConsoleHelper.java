package ru.summerpractice.hepler;


import ru.summerpractice.model.interfaces.IOServiceInterface;

import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleHelper implements IOServiceInterface {
    private final Scanner scanner = new Scanner(System.in);
    private final PrintStream out = System.out;

    @Override
    public String readString(String prompt) {
        out.print(prompt);
        return scanner.nextLine().trim();
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        while (true) {
            out.print(prompt);
            try {
                int v = Integer.parseInt(scanner.nextLine().trim());
                if (v < min || v > max) {
                    out.println("Введите число от " + min + " до " + max + ".");
                } else {
                    return v;
                }
            } catch (NumberFormatException e) {
                out.println("Неверный формат. Попробуйте снова.");
            }
        }
    }

    @Override
    public boolean readBoolean(String prompt) {
        while (true) {
            out.print(prompt);
            String s = scanner.nextLine().trim().toLowerCase();
            if (s.equals("y") || s.equals("yes") || s.equals("д")) return true;
            if (s.equals("n") || s.equals("no") || s.equals("н")) return false;
            out.println("Введите y/n.");
        }
    }

    @Override
    public void print(String message) {
        out.print(message);
    }

    @Override
    public void println(String message) {
        out.println(message);
    }
}