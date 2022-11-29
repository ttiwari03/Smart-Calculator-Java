package calculator;

import java.math.BigInteger;
import java.util.Stack;

/*
 *  Calculate value of postfix notation expression.
 */
public class PostfixNotationCalculator {
    protected static String calculatePostfixNotation(String postfixData) {
        Stack<String> stack = new Stack<>();

        for (String s : postfixData.split(" ")) {
            if (s.matches("^([+\\-])?[A-Za-z\\d]+")) {
                stack.push(s);
            } else {
                BigInteger num1;
                String stackValue1 = stack.pop();
                try {
                    num1 = new BigInteger(stackValue1);
                } catch (Exception e) {
                    num1 = Main.data.getOrDefault(stackValue1, InputProcessor.defaultValue);
                    if (num1 == InputProcessor.defaultValue) {
                        System.out.println(InputProcessor.unknownVariable);
                        break;
                    }
                }

                BigInteger num2;
                String stackValue2 = stack.pop();
                try {
                    num2 = new BigInteger(stackValue2);
                } catch (Exception e) {
                    num2 = Main.data.getOrDefault(stackValue2, InputProcessor.defaultValue);
                    if (num2 == InputProcessor.defaultValue) {
                        System.out.println(InputProcessor.unknownVariable);
                        break;
                    }
                }
                stack.push(calculate(num1, num2, s));
            }
        }

        return stack.pop();
    }

    private static String calculate(BigInteger num1, BigInteger num2, String operator) {
        BigInteger result = BigInteger.ZERO;
        switch (operator) {
            case "+" -> result = num2.add(num1);
            case "-" -> result = num2.subtract(num1);
            case "*" -> result = num2.multiply(num1);
            case "/" -> result = num2.divide(num1);
            case "^" -> result = num2.pow(num1.intValue());
        }
        return String.valueOf(result);
    }
}
