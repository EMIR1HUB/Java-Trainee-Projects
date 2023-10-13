package io.ylab.intensive.lesson02.SnilsValidator;

public class SnilsValidatorTest {
    public static void main(String[] args) {
        System.out.println("СНИЛС 01468870570: " + new SnilsValidatorImpl().validate("01468870570") + "\n");
        System.out.println("СНИЛС 90114404441: " + new SnilsValidatorImpl().validate("90114404441") + "\n");
        System.out.println("СНИЛС 592 599 039 55: " + new SnilsValidatorImpl().validate("592 599 039 55") + "\n");
        System.out.println("СНИЛС 901144044: " + new SnilsValidatorImpl().validate("901144044") + "\n");
        System.out.println("СНИЛС 473P129H758=02: " + new SnilsValidatorImpl().validate("473P129H758=02") + "\n");
    }
}
