package calculator;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/*
 *  This calculator supports
 *      - addition,
 *      - subtraction,
 *      - multiplication,
 *      - division,
 *      - exponential operations
 *      - and loading / saving data.
 */
public class Main {

    public static final Map<String, BigInteger> data = new LinkedHashMap<>();

    public static void main(String[] args) {

        Scanner readIp = new Scanner(System.in);
        boolean endProgram = false;
        
        System.out.println("This calculator supports addition, subtraction, multiplication, division, exponential operations and loading / saving data.");
        System.out.println();
        while (!endProgram) {
            System.out.println("Enter expression to calculate. (type /help for help, /exit for quitting)");
            System.out.println();
            String input = readIp.nextLine().trim();

            if (input.equals(Command.EXIT.getCommand())) {
                endProgram = true;
            } else if (input.trim().length() == 0) {
                continue;
            } else if (input.charAt(0) == '/') {
                processCommand(input);
            } else {
                if (input.matches("[+/-]\\d+")) {
                    System.out.println(input);
                } else {
                    if (input.contains("=")) {
                        String[] in = input.trim().split("=");
                        String value = in[1].trim();
                        if (value.matches("[A-Za-z]*\\d+[A-Za-z]+")
                                || value.matches("[A-Za-z]+\\d+[A-Za-z]*")) {
                            System.out.println(InputProcessor.invalidAssignment);
                        }
                        else if (InputProcessor.isValidAssignment(value)) {
                            processAssignment(in);
                        } else if (InputProcessor.isValidExpression(value)){
                            processExpressionAssignment(in);
                        }
                    } else if (input.matches(InputProcessor.alphabetRegex)) {
                        loadData(input);
                    } else if (InputProcessor.isValidExpression(input)) {
                        calculator(input);
                    }
                }
            }
        }

        System.out.println("Bye!");
    }

    private static void calculator(String expression) {
        expression = InputProcessor.formatInput(expression);
        if (InputProcessor.isValidExpression(expression)) {
            String postfixExpression = InfixToPostfixConvertor.infixToPostfix(expression.split(InputProcessor.space));
            String result = PostfixNotationCalculator.calculatePostfixNotation(postfixExpression);
            System.out.println(result);
        }
    }

    private static void loadData(String input) {
        if (InputProcessor.isValidIdentifier(input)
                && InputProcessor.isValidVariable(new String[]{input})) {
            System.out.println(data.get(input));
        }
    }

    private static void processAssignment(String[] input) {
        String variable = input[0].trim();
        String value = input[1].trim();
        if (InputProcessor.isValidIdentifier(variable)
            && InputProcessor.isValidAssignment(value)
        ) {
            saveData(input);
        }
    }

    private static void saveData(String[] input) {
        String variable = input[0].trim();
        String value = input[1].trim();
        if (InputProcessor.isValidVariable(new String[]{value})) {
            if (value.matches(InputProcessor.alphabetRegex)) {
                data.put(variable, data.get(value));
            } else {
                data.put(variable, new BigInteger(value));
            }
        }
    }

    private static void processExpressionAssignment(String[] input) {
        String variable = input[0].trim();
        String expression = input[1].trim();
        expression = InputProcessor.formatInput(expression);

        if (InputProcessor.isValidIdentifier(variable)
            && InputProcessor.isValidExpression(expression)) {
            String postfixExpression = InfixToPostfixConvertor.infixToPostfix(expression.split(InputProcessor.space));
            String result = PostfixNotationCalculator.calculatePostfixNotation(postfixExpression);
            data.put(variable, new BigInteger(result));
        }
    }

    private static void processCommand(String input) {
        if (InputProcessor.isValidCommand(input)) {
            if ("/help".equals(input)) {
                System.out.println("This calculator supports addition, subtraction, multiplication, division, exponential operations and loading / saving data.");
            }
        }
    }
}
