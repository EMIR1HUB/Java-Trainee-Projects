package io.ylab.intensive.lesson01;

import java.util.Scanner;

public class Pell {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            int a = 0, b = 1, c = 0;

            for (int i = 0; i < n; i++) {
                c = a + 2 * b;
                a = b;
                b = c;
            }
            System.out.println(a);
        }
    }
}
