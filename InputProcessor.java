package calculator;

import java.math.BigInteger;

/*
 *  Check input validity and convert it to formatted expression for processing.
 */
public class InputProcessor {

    protected static final String invalidAssignment = "Invalid assignment";
    protected static final String invalidExpression = "Invalid expression";
    protected static final String invalidIdentifier = "Invalid identifier";
    protected static final String unknownCommand = "Unknown command";
    protected static final String unknownVariable = "Unknown variable";
    protected static final String operators = "+-*/^()=";
    protected static final String space = " ";
    protected static final String alphabetRegex = "(\\+|-)?[A-Za-z]+";
    protected static final String numberRegex = "(\\+|-)?\\d+";
    protected static final String expressionRegex = "([+\\-*/^\\sA-Za-z\\d()]*\\)$)|([+\\-*/^\\sA-Za-z\\d()]*[A-Za-z\\d]+$)";
    //[+\-*\/^\sA-Za-z\d()]*\)$ - ending with bracket
    //[+\-*\/^\sA-Za-z\d()]*[A-Za-z\d]+$ - ending with number/variable
    protected static final BigInteger defaultValue = null;

    protected static boolean isValidIdentifier(String identifier) {

        if (identifier.matches(alphabetRegex)) {
            return true;
        }

        System.out.println(invalidIdentifier);
        return false;
    }

    protected static boolean isValidAssignment(String assignment) {

        if (assignment.matches(alphabetRegex) || assignment.matches(numberRegex)) {
            return true;
        }

        System.out.println(invalidAssignment);
        return false;
    }

    protected static boolean isValidExpression(String input) {

        if (isBracketBalanced(input.split(space))) {
            if (input.matches(expressionRegex)
                    && !input.contains("* *")
                    && !input.contains("/ /")
                    && !input.contains("^ ^")
            ) {
                return true;
            }
        }

        System.out.println(invalidExpression);
        return false;
    }

    private static boolean isBracketBalanced(String[] input) {
        int bracketCount = 0;

        for (String element : input) {
            if (element.equals("(")) {
                bracketCount++;
            }

            if (element.equals(")") && bracketCount > 0) {
                bracketCount--;
            } else if (element.equals(")") && bracketCount == 0){
                return false;
            }
        }

        return bracketCount == 0;
    }

    protected static boolean isValidCommand(String inputCommand) {
        for (Command command : Command.values()) {
            if (inputCommand.equals(command.getCommand())) {
                return true;
            }
        }

        //  If command not found return false.
        System.out.println(unknownCommand);
        return false;
    }

    protected static boolean isValidVariable(String[] input) {

        for (String element : input) {
            if (element.matches(alphabetRegex)) {
                BigInteger value = Main.data.getOrDefault(element, defaultValue);

                //  If value not found.
                if (value == defaultValue) {
                    System.out.println(unknownVariable);
                    return false;
                }
            }
        }

        //  If variable is in data.
        return true;
    }

    protected static String formatInput(String input) {

        StringBuilder formattedInput = new StringBuilder();

        //  Insert space around every operator.
        for (int i = 0; i < input.length(); i++) {
            String element = input.substring(i, i + 1);
            if (operators.contains(element)) {
                formattedInput.append(space.concat(element).concat(space));
            } else {
                formattedInput.append(element);
            }
        }

        return formattedInput.toString()
                .replaceAll("-\\s*-", "+ ") // Replace all "--" with "+"
                .replaceAll("(\\+\\s*)+", "+ ") // Replace more than one "+" with single "+"
                .replaceAll("(\\+\\s*-)|(-\\s*\\+)", "-")  // Replace all "+-" / "-+" with "-"
                .replaceAll("\\s+", space)
                .trim();    // Replace all extra space with single space
    }

}

