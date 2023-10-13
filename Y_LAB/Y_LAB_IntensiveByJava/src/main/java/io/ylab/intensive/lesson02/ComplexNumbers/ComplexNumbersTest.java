package io.ylab.intensive.lesson02.ComplexNumbers;

public class ComplexNumbersTest {
    public static void main(String[] args) {
        ComplexNumbers complex1 = new ComplexNumbers(5, 4);
        ComplexNumbers complex2 = new ComplexNumbers(3, 6);
        ComplexNumbers complex3 = new ComplexNumbers(-6);
        System.out.println(complex1);
        System.out.println(complex2);
        System.out.println(complex3);

        System.out.println("\nОперации с complex1 и complex2");
        System.out.println("Plus : " + complex1.plus(complex2));
        System.out.println("Minus : " + complex1.minus(complex2));
        System.out.println("Multiplication : " + complex1.multi(complex2));
        System.out.println("Abc : " + complex1.abc());

        System.out.println("\nОперации с complex3 и complex1");
        System.out.println("Plus : " + complex3.plus(complex1));
        System.out.println("Minus : " + complex3.minus(complex1));
        System.out.println("Multiplication : " + complex3.multi(complex1));
        System.out.println("Abc : " + complex3.abc());
    }
}
