package io.ylab.intensive.lesson03.PasswordValidator.Exceptions;

public class WrongLoginException extends Exception {
    private final String detail;

    public WrongLoginException() {
        this.detail = "Incorrect login!";
    }

    public WrongLoginException(String message) {
        super(message);
        detail = message;
    }

    @Override
    public String toString() {
        return "WrongLoginException: " + detail;
    }
}
