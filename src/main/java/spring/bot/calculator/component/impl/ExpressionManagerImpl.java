package spring.bot.calculator.component.impl;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.bot.calculator.component.ExpressionManager;
import spring.bot.calculator.component.MathOperation;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Stack;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ExpressionManagerImpl implements ExpressionManager {
    private static final String OPEN_BRACKET = "(";
    private static final String CLOSE_BRACKET = ")";

    private final Map<String, MathOperation> operationMap;

    @Autowired
    public ExpressionManagerImpl(List<MathOperation> operationList) {
        this.operationMap = operationList.stream()
                .collect(Collectors.toMap(MathOperation::getSignature, Function.identity()));
    }

    @Override
    public Double calculate(String expression) {
        List<String> items = prepareExpression(expression);
        Queue<String> rpn = convertToRPN(items);
        return calculateExpression(rpn);
    }

    public List<String> prepareExpression(final String expression) {
        List<String> output = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        boolean isBeginOperation = true;
        for (final char c : expression.toCharArray()) {
            if (Character.isDigit(c) || '.' == c || (isBeginOperation && '-' == c)) {
                buffer.append(c);
                isBeginOperation = false;
            } else {
                if (buffer.length() > 0) {
                    output.add(buffer.toString());
                    buffer = new StringBuilder();
                }
                if (!Character.isWhitespace(c)) {
                    output.add(String.valueOf(c));
                    isBeginOperation = !CLOSE_BRACKET.equals(String.valueOf(c));
                }
            }
        }
        if (buffer.length() > 0) {
            output.add(buffer.toString());
        }

        return output;
    }

    public Queue<String> convertToRPN(final List<String> items) {
        Queue<String> output = new ArrayDeque<>();
        Stack<String> operations = new Stack<>();
        items.forEach(item -> {
            if (NumberUtils.isCreatable(item)) {
                output.add(item);
            } else {
                if (OPEN_BRACKET.equals(item)) {
                    operations.push(item);
                }
                if (CLOSE_BRACKET.equals(item)) {
                    while (!operations.empty()) {
                        if (OPEN_BRACKET.equals(operations.peek())) {
                            operations.pop();
                            break;
                        }
                        output.add(operations.pop());
                    }
                }
                if (operationMap.containsKey(item)) {
                    if (!operations.empty() && getPriority(operations.peek()) <= getPriority(item)) {
                        output.add(operations.pop());
                    }
                    operations.push(item);
                }
            }
        });
        while (!operations.empty()) {
            output.add(operations.pop());
        }

        return output;
    }

    private Double calculateExpression(final Queue<String> expression) {
        Stack<Double> stack = new Stack<>();
        while (expression.peek() != null) {
            String item = expression.poll();
            if (NumberUtils.isCreatable(item)) {
                stack.push(NumberUtils.createDouble(item));
            } else {
                MathOperation math = operationMap.get(item);
                if (math != null) {
                    Double result = math.calculate(stack);
                    stack.push(result);
                } else {
                    throw new IllegalStateException();
                }
            }
        }
        return stack.pop();
    }

    private int getPriority(final String operation) {
        return Optional.ofNullable(operationMap.get(operation))
                .map(MathOperation::getPriority)
                .orElse(Integer.MAX_VALUE);
    }
}
