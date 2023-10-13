package io.ylab.intensive.lesson02.SnilsValidator;

public class SnilsValidatorImpl implements SnilsValidator {
    @Override
    public boolean validate(String snils) {
        String workSnils = onlyDigit(snils);
        boolean result = false;

        if (workSnils.length() == 11) {
            int controlSum = SNILSControlCalc(workSnils);
            int strControlSum = Integer.parseInt(workSnils.substring(9));
            if (controlSum == strControlSum) {
                result = true;
            }
        } else {
            System.out.printf("СНИЛС %s не состоит из 11 цифр\n", workSnils);
        }
        return result;
    }

    private String onlyDigit(String snils) {    //Избавляемся от лишних символов
        String correctStr = "";
        for (int i = 0; i < snils.length(); i++) {
            if (Character.isDigit(snils.charAt(i))) {
                correctStr += snils.charAt(i);
            }
        }
        return correctStr;
    }

    private int SNILSControlCalc(String snils) {
        int totalSum = 0;
        String workSnils = snils.substring(0, 9);

        for (int i = workSnils.length() - 1, j = 0; i >= 0; i--, j++) {
            int digit = Integer.parseInt(String.valueOf(workSnils.charAt(i)));
            totalSum += digit * (j + 1);
        }
        return SNILSCheckControlSum(totalSum);
    }

    private int SNILSCheckControlSum(int controlSum) {
        if (controlSum == 100) {
            return 0;
        } else if (controlSum < 100) {
            return controlSum;
        } else {
            int tempCalc = controlSum % 101;
            return SNILSCheckControlSum(tempCalc);
//            return tempCalc == 100 ? 0 : tempCalc;
        }
    }
}
