package ru.summerpractice.hepler;

import java.util.Scanner;

public class ConsoleHelper {
    private static final Scanner SC = new Scanner(System.in);

    public static String readString(String prompt) {
        System.out.print(prompt);
        return SC.nextLine().trim();
    }

    public static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int v = Integer.parseInt(SC.nextLine().trim());
                if (v < min || v > max) {
                    System.out.println("Введите число от " + min + " до " + max + ".");
                } else {
                    return v;
                }
            } catch (NumberFormatException e) {
                System.out.println("Неверный формат. Попробуйте снова.");
            }
        }
    }

    public static boolean readBoolean(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = SC.nextLine().trim().toLowerCase();
            if (s.equals("y") || s.equals("yes") || s.equals("д")) return true;
            if (s.equals("n") || s.equals("no") || s.equals("н")) return false;
            System.out.println("Введите y/n.");
        }
    }
}

