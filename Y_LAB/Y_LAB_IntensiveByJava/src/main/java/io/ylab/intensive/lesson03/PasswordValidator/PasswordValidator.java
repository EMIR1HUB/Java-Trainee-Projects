package io.ylab.intensive.lesson03.PasswordValidator;

import io.ylab.intensive.lesson03.PasswordValidator.Exceptions.WrongLoginException;
import io.ylab.intensive.lesson03.PasswordValidator.Exceptions.WrongPasswordException;

public class PasswordValidator {
    public static boolean Login(String login, String password, String confirmPassword) {
        try {
            if (!login.matches("[A-Za-z0-9_]+")) {
                throw new WrongLoginException("Логин содержит недопустимые символы");
            }
            else if (login.length() > 20) {
                throw new WrongLoginException("Логин слишком длинный");
            }
            else if (!password.matches("[A-Za-z0-9_]+")) {
                throw new WrongLoginException("Пароль содержит недопустимые символы");
            }
            else if (password.length() > 20) {
                throw new WrongPasswordException("Пароль слишком длинный");
            }
            else if (!password.equals(confirmPassword)) {
                throw new WrongPasswordException("Пароль и подтверждение не совпадают");
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
}

// Через ASCII код
//    for (int i = 0; i < login.length(); i++) {
//    char ch = login.charAt(i);
//    if (!(ch >= 'A' && ch <= 'Z' || ch >= 'a' && ch <= 'z' || ch >= '0' && ch <= '9' || ch == '_')) {
//    throw new WrongLoginException("Логин содержит недопустимые символы");
//    } else if (login.length() > 20) {
//    throw new WrongLoginException("Логин слишком длинный");
//    }
//    }
//    for (int i = 0; i < password.length(); i++) {
//    char ch = password.charAt(i);
//    if (!(ch >= 'A' && ch <= 'Z' || ch >= 'a' && ch <= 'z' || ch >= '0' && ch <= '9' || ch == '_')) {
//    throw new WrongPasswordException("Пароль содержит недопустимые символы");
//    } else if (password.length() > 20) {
//    throw new WrongPasswordException("Пароль слишком длинный");
//    } else if (!password.equals(confirmPassword)) {
//    throw new WrongPasswordException("Пароль и подтверждение не совпадают");
//    }
//    }
