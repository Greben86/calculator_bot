package spring.bot.calculator.component.impl;

import org.springframework.stereotype.Component;
import spring.bot.calculator.component.OperationInfo;
import spring.bot.calculator.component.OperationPriority;

import java.util.Stack;

@Component
@OperationInfo(name = "!", priority = OperationPriority.HIGH)
public class OperationFactorial extends AbstractOperation {

    @Override
    public double calculate(Stack<Double> stack) {
        Double a = getOperand(stack);
        if (a < 0) {
            throw new IllegalStateException();
        }

        return factorial(a.longValue());
    }

    private long factorial(long operand) {
        return operand > 0L ? factorial(operand - 1L) * operand : 1L;
    }
}
