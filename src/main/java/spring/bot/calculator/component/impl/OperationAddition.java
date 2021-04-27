package spring.bot.calculator.component.impl;

import org.springframework.stereotype.Component;

import java.util.Stack;

@Component
public class OperationAddition extends AbstractOperation {

    OperationAddition() {
        super("+", 3);
    }

    @Override
    public double calculate(Stack<Double> stack) {
        Double b = getOperand(stack);
        Double a = getOperand(stack);
        return Double.sum(a, b);
    }
}
