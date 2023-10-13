package io.ylab.intensive.lesson03.PasswordValidator.Exceptions;

public class WrongPasswordException extends Exception{
    private final String detail;

    public WrongPasswordException() {
        this.detail = "Incorrect login!";
    }

    public WrongPasswordException(String message) {
        super(message);
        this.detail = message;
    }

    @Override
    public String toString() {
        return "WrongPasswordException: " + detail;
    }
}
