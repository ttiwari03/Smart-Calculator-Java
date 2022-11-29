package calculator;

import java.util.Stack;

/*
 *  Convert infix expression to postfix expression.
 */

public class InfixToPostfixConvertor {
    protected static String infixToPostfix(String[] input) {
        String precedenceOrder = "+-/*^";
        Stack<String> stack = new Stack<>();
        StringBuilder postfixData = new StringBuilder();

        for (String s : input) {
            if (s.matches("[A-Za-z\\d]+")) {
                postfixData.append(s.concat(" "));
            } else {
                String top = !stack.empty() ? stack.peek() : "";
                if (s.equals("(")
                        || stack.empty()
                        || top.equals("(")
                        || precedenceOrder.indexOf(top) < precedenceOrder.indexOf(s)) {
                    stack.push(s);
                } else {
                    if (s.equals(")")) {
                        while (!top.equals("(")) {
                            String stackValue = !stack.empty() ? stack.pop() : "";
                            stackValue = stackValue.equals("(") ? "" : stackValue.concat(" ");
                            postfixData.append(stackValue);
                            top = !stack.empty() ? stack.peek() : "";
                        }
                        stack.pop();
                    } else {
                        while (!top.equals("") && precedenceOrder.indexOf(top) >= precedenceOrder.indexOf(s)) {
                            String stackValue = !stack.empty() ? stack.pop() : "";
                            stackValue = stackValue.equals("(") ? "" : stackValue.concat(" ");
                            postfixData.append(stackValue);
                            top = !stack.empty() ? stack.peek() : "";
                        }
                        stack.push(s);
                    }
                }
            }
        }

        while (!stack.empty()) {
            String stackValue = stack.pop();
            stackValue = stackValue.equals("(") ? "" : stackValue.concat(" ");
            postfixData.append(stackValue);
        }

        return postfixData.toString();
    }
}
