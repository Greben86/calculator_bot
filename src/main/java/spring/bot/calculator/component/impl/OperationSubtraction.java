package spring.bot.calculator.component.impl;

import org.springframework.stereotype.Component;
import spring.bot.calculator.component.OperationInfo;
import spring.bot.calculator.component.OperationPriority;

import java.util.Stack;

@Component
@OperationInfo(name = "-", priority = OperationPriority.LOW)
public class OperationSubtraction extends AbstractOperation {

    @Override
    public double calculate(Stack<Double> stack) {
        Double b = getOperand(stack);
        if (stack.empty()) {
            return -b;
        }

        Double a = getOperand(stack);
        return a - b;
    }
}
