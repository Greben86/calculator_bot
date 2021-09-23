package spring.bot.calculator.component.impl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.bot.calculator.component.MathOperation;

import java.util.Stack;

@NoArgsConstructor
@Getter
public abstract class AbstractOperation implements MathOperation {
    private String signature;
    private Integer priority;

    protected Double getOperand(Stack<Double> stack) {
        if (stack.empty()) {
            throw new IllegalStateException();
        }

        return stack.pop();
    }
}
