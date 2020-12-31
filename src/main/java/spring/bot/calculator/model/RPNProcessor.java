package spring.bot.calculator.model;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

public class RPNProcessor {
    private static final char OPEN_BRACKET = '(';
    private static final char CLOSE_BRACKET = ')';
    private static final String ALL_DIGITS = "0123456789.";
    private static final String ALL_OPERATIONS = "+-*/^";

    public static Queue<String> convertToRPN(final String input) {
        return convertToRPN(input.toCharArray());
    }

    public static Queue<String> convertToRPN(final char[] input) {
        Queue<String> output = new ArrayDeque<>();
        Stack<Character> operations = new Stack<>();
        StringBuilder buffer = new StringBuilder();
        for (char c : input) {
            if (isDigit(c)) {
                buffer.append(c);
            } else
                if (buffer.length() > 0) {
                    output.add(buffer.toString());
                    buffer = new StringBuilder();
                }
            if (isOpenBracket(c)) {
                operations.push(c);
            }
            if (isCloseBracket(c)) {
                while (!operations.empty()) {
                    if (isOpenBracket(operations.peek())) {
                        operations.pop();
                        break;
                    }
                    output.add(String.valueOf(operations.pop()));
                }
            }
            if (isOperation(c)) {
                if (!operations.empty() && getPriority(operations.peek()) <= getPriority(c)) {
                    output.add(String.valueOf(operations.pop()));
                }
                operations.push(c);
            }
        }
        if (buffer.length() > 0) {
            output.add(buffer.toString());
        }
        while (!operations.empty()) {
            output.add(String.valueOf(operations.pop()));
        }

        return output;
    }

    public static String calculateExpression(final Queue<String> expression) {
        Stack<String> stack = new Stack<>();
        while (expression.peek() != null) {
            if (isDigit(expression.peek())) {
                stack.push(expression.poll());
            } else {
                double y = Double.parseDouble(stack.pop());
                double x = Double.parseDouble(stack.pop());
                double z = executeOperation(expression.poll(), x, y);
                stack.push(String.valueOf(z));
            }
        }
        return stack.pop();
    }

    private static double executeOperation(final String operation, final double operand1, final double operand2) {
        switch (operation) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                return operand1 / operand2;
            case "^":
                return Math.pow(operand1, operand2);
            default:
                return 0.;
        }
    }

    private static boolean isDigit(final String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        return isDigit(value.charAt(0));
    }

    private static boolean isDigit(final char value) {
        return ALL_DIGITS.indexOf(value) >= 0;
    }

    private static boolean isOperation(final char value) {
        return ALL_OPERATIONS.indexOf(value) >= 0;
    }

    private static boolean isOpenBracket(final char value) {
        return OPEN_BRACKET == value;
    }

    private static boolean isCloseBracket(final char value) {
        return CLOSE_BRACKET == value;
    }

    private static int getPriority(final char operation) {
        switch (operation) {
            case '^':
                return 1;
            case '*':
            case '/':
                return 2;
            case '+':
            case '-':
                return 3;
            case '(':
            case ')':
                return 4;
            default:
                return 0;
        }
    }
}
