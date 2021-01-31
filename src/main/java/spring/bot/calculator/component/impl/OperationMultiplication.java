package spring.bot.calculator.component.impl;

import org.springframework.stereotype.Component;

import java.util.Stack;

@Component
public class OperationMultiplication extends AbstractOperation {

    OperationMultiplication() {
        super("*", 2);
    }

    @Override
    public double calculate(Stack<Double> stack) {
        Double b = getNextOperand(stack);
        Double a = getNextOperand(stack);
        return a * b;
    }
}
