package spring.bot.calculator.component;

import java.util.Stack;

public interface MathOperation {
    String getSignature();
    Integer getPriority();
    double calculate(Stack<Double> stack);
}
