package io.ylab.intensive.lesson01;

import java.util.Random;
import java.util.Scanner;

public class Guess {
    public static void main(String[] args) {
        int number = new Random().nextInt(99) + 1; // здесь загадывается число от 1 до 99
        int maxAttempts = 10; // здесь задается количество попыток
        System.out.println("Я загадал число. У тебя " + maxAttempts + " попыток угадать.");
        try (Scanner scanner = new Scanner(System.in)) {
            for (int i = 1; i <= maxAttempts; i++) {
                int n = scanner.nextInt();
                if (n == number) {
                    System.out.println("Ты угадал с " + i + " попытки");
                    System.exit(0);
                }
                if (n > number) {
                    System.out.println("Мое число меньше! У тебя осталось " + (maxAttempts - i) + " попыток");
                }
                if (n < number) {
                    System.out.println("Мое число больше! У тебя осталось " + (maxAttempts - i) + " попыток");
                }
            }
            System.out.println("Ты не угадал");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
