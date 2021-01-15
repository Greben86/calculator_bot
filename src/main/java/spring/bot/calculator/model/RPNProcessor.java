package spring.bot.calculator.model;

import lombok.AllArgsConstructor;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;
import java.util.function.BinaryOperator;

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
            if (OPEN_BRACKET == c) {
                operations.push(c);
            }
            if (CLOSE_BRACKET == c) {
                while (!operations.empty()) {
                    if (OPEN_BRACKET == operations.peek()) {
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

    public static Double calculateExpression(final Queue<String> expression) {
        Stack<Double> stack = new Stack<>();
        while (expression.peek() != null) {
            String item = expression.poll();
            if (isDigit(item)) {
                stack.push(Double.parseDouble(item));
            } else {
                PairHolder<Double> holder = getTwoOperand(stack);
                stack.push(executeOperation(item, holder));
            }
        }
        return stack.pop();
    }

    private static PairHolder<Double> getTwoOperand(final Stack<Double> stack) {
        Double right = getOneOperand(stack);
        Double left = getOneOperand(stack);

        return new PairHolder<>(left, right);
    }

    private static Double getOneOperand(final Stack<Double> stack) {
        if (stack.empty()) {
            throw new IllegalStateException();
        }

        return stack.pop();
    }

    private static double executeOperation(final String operation, final PairHolder<Double> holder) {
        switch (operation) {
            case "+":
                return holder.apply(Double::sum);
            case "-":
                return holder.apply((a, b) -> a - b);
            case "*":
                return holder.apply((a, b) -> a * b);
            case "/":
                return holder.apply((a, b) -> a / b);
            case "^":
                return holder.apply(Math::pow);
            default:
                throw new IllegalStateException();
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

    @AllArgsConstructor
    private static class PairHolder<T> {
        private final T leftOperand;
        private final T rightOperand;

        public T apply(BinaryOperator<T> operator) {
            return operator.apply(leftOperand, rightOperand);
        }
    }
}
