package spring.bot.calculator.component.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import spring.bot.calculator.component.MathOperation;

import java.util.Stack;

@AllArgsConstructor
@Getter
public abstract class AbstractOperation implements MathOperation {
    private final String signature;
    private final int priority;

    protected Double getNextOperand(Stack<Double> stack) {
        if (stack.empty()) {
            throw new IllegalStateException();
        }

        return stack.pop();
    }
}
