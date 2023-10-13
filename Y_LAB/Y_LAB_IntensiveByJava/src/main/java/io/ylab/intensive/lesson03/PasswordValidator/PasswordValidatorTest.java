package io.ylab.intensive.lesson03.PasswordValidator;

public class PasswordValidatorTest {
    public static void main(String[] args) {
        // валидный варинат
        System.out.println(PasswordValidator.Login("Emir_2003", "123", "123"));
        System.out.println(PasswordValidator.Login("Y_Lab2023", "y_lab2023", "y_lab2023"));

        //не валидный
        System.out.println(PasswordValidator.Login("Y-Lab2023", "y-lab2023", "y-lab2023"));
        System.out.println(PasswordValidator.Login("YLab2023202303030303030", "y-lab2023", "y-lab"));
        System.out.println(PasswordValidator.Login("YLab_5", "y_lab2023", "y_lab"));
        System.out.println(PasswordValidator.Login("YLab_5", "y-lab", "y-lab"));
        System.out.println(PasswordValidator.Login("YLab_5", "YLab2023202303030303030", "YLab2023202303030303030"));
    }
}
