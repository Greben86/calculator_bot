package spring.bot.calculator.model;

import org.junit.jupiter.api.Test;
import spring.bot.calculator.component.RPNProcessor;

import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RPNProcessorTest {

    @Test
    void convertToRPN() {
        String rpnExpression = String.join(" ", RPNProcessor.convertToRPN("3 + (4 * 2) / (1 - 5)^2"));
        assertEquals("3 4 2 * 1 5 - 2 ^ / +", rpnExpression);

        rpnExpression = String.join(" ", RPNProcessor.convertToRPN("5*5*(4-2)^3"));
        assertEquals("5 5 * 4 2 - 3 ^ *", rpnExpression);

        rpnExpression = String.join(" ", RPNProcessor.convertToRPN("10*(2+15)"));
        assertEquals("10 2 15 + *", rpnExpression);

        rpnExpression = String.join(" ", RPNProcessor.convertToRPN("0.5*7"));
        assertEquals("0.5 7 *", rpnExpression);

        rpnExpression = String.join(" ", RPNProcessor.convertToRPN("(6+10-4)/(1+1*2)+1"));
        assertEquals("6 10 + 4 - 1 1 2 * + / 1 +", rpnExpression);
    }

    @Test
    void calculateExpression() {
        Queue<String> rpnExpression = RPNProcessor.convertToRPN("3 + (4 * 2) / (1 - 5)^2");
        Double result = RPNProcessor.calculateExpression(rpnExpression);
        assertEquals(3.5, result);

        rpnExpression = RPNProcessor.convertToRPN("5*5*(4-2)^3");
        result = RPNProcessor.calculateExpression(rpnExpression);
        assertEquals(200., result);

        rpnExpression = RPNProcessor.convertToRPN("10*(2+15)");
        result = RPNProcessor.calculateExpression(rpnExpression);
        assertEquals(170., result);

        rpnExpression = RPNProcessor.convertToRPN("0.5*7");
        result = RPNProcessor.calculateExpression(rpnExpression);
        assertEquals(3.5, result);

        rpnExpression = RPNProcessor.convertToRPN("(6+10-4)/(1+1*2)+1");
        result = RPNProcessor.calculateExpression(rpnExpression);
        assertEquals(5., result);
    }
}