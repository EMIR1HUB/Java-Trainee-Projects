package io.ylab.intensive.lesson02.Sequences;

public class SequencesImpl implements Sequences {

    private boolean checkOnCorrectData(int n) {
        if (n > 0 && n <= 10) {
            return true;
        } else {
            System.out.printf("Число %d не лежит в диапозоне от 1 до 10", n);
            return false;
        }
    }

    @Override
    public void a(int n) {
        System.out.print("A. ");
        if (checkOnCorrectData(n)) {
            int numberSequence = 0;
            for (int i = 0; i < n; i++) {
                System.out.print((numberSequence += 2) + " ");
            }
        }
        System.out.println();
    }

    @Override
    public void b(int n) {
        System.out.print("B. ");
        if (checkOnCorrectData(n)) {
            int numberSequence = -1;
            for (int i = 0; i < n; i++) {
                System.out.print((numberSequence += 2) + " ");
            }
        }
        System.out.println();
    }

    @Override
    public void c(int n) {
        System.out.print("C. ");

        if (checkOnCorrectData(n)) {
            int numberSequence = 1, numberForAdd = 3;
            System.out.print(numberSequence + " ");
            for (int i = 1; i < n; i++) {
                System.out.print((numberSequence += numberForAdd) + " ");
                numberForAdd += 2;
            }
        }
        System.out.println();
    }

    @Override
    public void d(int n) {
        System.out.print("D. ");
        if (checkOnCorrectData(n)) {
            for (int i = 1; i <= n; i++) {
                System.out.print((i * i * i) + " ");
            }
        }
        System.out.println();
    }

    @Override
    public void e(int n) {
        System.out.print("E. ");
        if (checkOnCorrectData(n)) {
            int numberSequence = -1;
            for (int i = 1; i <= n; i++) {
                System.out.print((numberSequence *= -1) + " ");
            }
        }
        System.out.println();
    }

    @Override
    public void f(int n) {
        System.out.print("F. ");
        if (checkOnCorrectData(n)) {
            int numberForSign = 1;
            for (int i = 1; i <= n; i++) {
                System.out.print((i * numberForSign) + " ");
                numberForSign *= -1;
            }
        }
        System.out.println();
    }

    @Override
    public void g(int n) {
        System.out.print("G. ");
        if (checkOnCorrectData(n)) {
            int numberForSign = 1;
            for (int i = 1; i <= n; i++) {
                System.out.print((i * i * numberForSign) + " ");
                numberForSign *= -1;
            }
        }
        System.out.println();
    }

    @Override
    public void h(int n) {
        System.out.print("H. ");
        if (checkOnCorrectData(n)) {
            int numberSequence = 0;
            for (int i = 1; i <= n; i++) {
                if (i % 2 == 0) {
                    System.out.print((0) + " ");
                } else {
                    System.out.print((i - numberSequence) + " ");
                    numberSequence = i - numberSequence;
                }
            }
        }
        System.out.println();
    }

    @Override
    public void i(int n) {
        System.out.print("I. ");
        if (checkOnCorrectData(n)) {
            int numberSequence = 1;
            for (int i = 1; i <= n; i++) {
                System.out.print((i * numberSequence) + " ");
                numberSequence *= i;
            }
        }
        System.out.println();
    }

    @Override
    public void j(int n) {      // числа фибоначчи
        System.out.print("J. ");
        if (checkOnCorrectData(n)) {
            int numberBefore = 0, numberAfter = 1, numberSequence;
            System.out.print(numberAfter + " ");
            for (int i = 2; i <= n; i++) {
                numberSequence = numberBefore + numberAfter;
                System.out.print(numberSequence + " ");

                numberBefore = numberAfter;
                numberAfter = numberSequence;
            }
        }
        System.out.println();
    }
}
