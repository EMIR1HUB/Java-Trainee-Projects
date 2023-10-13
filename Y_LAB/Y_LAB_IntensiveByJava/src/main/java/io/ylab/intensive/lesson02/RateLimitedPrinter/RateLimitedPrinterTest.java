package io.ylab.intensive.lesson02.RateLimitedPrinter;

public class RateLimitedPrinterTest {
    public static void main(String[] args) {
        System.out.println("TEST1");
        RateLimitedPrinter rateLimitedPrinter = new RateLimitedPrinterImpl(1000);
        for (int i = 0; i < 1_000_000_000; i++) {
            rateLimitedPrinter.print(String.valueOf(i));
        }

        System.out.println("\nTEST2");
        rateLimitedPrinter = new RateLimitedPrinterImpl(100);
        for (int i = 0; i < 1_000_000_000; i++) {
            rateLimitedPrinter.print(String.valueOf(i));
        }
    }
}
