import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Консольный калькулятор");
        System.out.println("Введите выражение (например, 2 + 3) или 'exit' для выхода:");

        String input;
        while (true) {
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                String result = calc(input);
                System.out.println("Результат: " + result);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }

        System.out.println("Программа завершена.");
    }

    public static String calc(String input) {
        String[] parts = input.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Неправильный формат выражения!");
        }

        String num1Str = parts[0];
        String operator = parts[1];
        String num2Str = parts[2];

        int num1, num2;
        try {
            num1 = parseInput(num1Str);
            num2 = parseInput(num2Str);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Недопустимые числа: " + e.getMessage());
        }

        int result;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 == 0) {
                    throw new IllegalArgumentException("Деление на ноль запрещено!");
                }
                result = num1 / num2;
                break;
            default:
                throw new IllegalArgumentException("Недопустимая операция!");
        }

        if (result < 1) {
            throw new IllegalArgumentException("Результат меньше единицы. Римские числа не могут быть отрицательными!");
        }

        return convertToOutputFormat(result, isRomanInput(num1Str, num2Str));
    }

    private static int parseInput(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return convertRomanToArabic(input);
        }
    }

    private static boolean isRomanInput(String num1Str, String num2Str) {
        return num1Str.matches("[IVXLCDM]+") && num2Str.matches("[IVXLCDM]+");
    }

    private static String convertToOutputFormat(int num, boolean isRoman) {
        return isRoman ? convertToRoman(num) : String.valueOf(num);
    }

    private static String convertToRoman(int num) {
        if (num < 1 || num > 3999) {
            throw new IllegalArgumentException("Римские числа могут быть только положительными и до 3999!");
        }

        StringBuilder sb = new StringBuilder();
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanSymbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                sb.append(romanSymbols[i]);
                num -= values[i];
            }
        }

        return sb.toString();
    }

    private static int convertRomanToArabic(String roman) {
        Map<Character, Integer> romanMap = new HashMap<>();
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
        romanMap.put('D', 500);
        romanMap.put('M', 1000);

        int result = 0;
        int prevValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            int currentValue = romanMap.get(roman.charAt(i));

            if (currentValue < prevValue) {
                result -= currentValue;
            } else {
                result += currentValue;
                prevValue = currentValue;
            }
        }

        return result;
    }
}
