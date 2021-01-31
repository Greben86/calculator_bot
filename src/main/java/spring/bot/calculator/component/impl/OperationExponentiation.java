package spring.bot.calculator.component.impl;

import org.springframework.stereotype.Component;

import java.util.Stack;

@Component
public class OperationExponentiation extends AbstractOperation {

    OperationExponentiation() {
        super("^", 1);
    }

    @Override
    public double calculate(Stack<Double> stack) {
        Double b = getNextOperand(stack);
        Double a = getNextOperand(stack);
        return Math.pow(a, b);
    }
}
