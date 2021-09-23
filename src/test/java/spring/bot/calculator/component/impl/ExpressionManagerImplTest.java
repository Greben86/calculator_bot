package spring.bot.calculator.component.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import spring.bot.calculator.component.ExpressionManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
class ExpressionManagerImplTest {

    @Autowired
    private ExpressionManager expressionManager;

    private Double calc(String expression) {
        return expressionManager.calculate(expression);
    }

    @Test
    void calculateAdd() {
        assertEquals(3., calc("1 + 2"));
        assertEquals(1., calc("1 + 0"));
        assertEquals(21., Math.round(calc("0.01 + 0.2") * 100));
        assertEquals(107.5, calc("-123.5 + 231"));
        assertEquals(-14, calc("-12 + -2"));
        assertEquals(10, calc("1 +2+ 3+4+ 5+-5"));
    }

    @Test
    void calculateSubtraction() {
        assertEquals(-1., calc("1 - 2"));
        assertEquals(1., calc("1 - 0"));
        assertEquals(0., calc("1 - 1"));
        assertEquals(-8., Math.round(calc("0.02 - 0.1") * 100));
        assertEquals(-354.5, calc("-123.5 - 231"));
        assertEquals(-10., calc("-12 - -2"));
        assertEquals(-54., calc("1.0 -2.0- 3-4- 51--5"));
    }

    @Test
    void calculateMultiplication() {
        assertEquals(4., calc("2 * 2"));
        assertEquals(0., calc("2 * 0"));
        assertEquals(0., calc("0 * 0"));
        assertEquals(4., calc("-2 * -2"));
        assertEquals(-50.5, calc("-1 * 50.5"));
        assertEquals(-123., calc("123 * -1"));
        assertEquals(6., Math.round(calc("0.3 * 0.2") * 100));
        assertEquals(-1260., calc("-1.0 * 21* -3*4.0*-5"));
    }

    @Test
    void calculateDivision() {
        assertEquals(1., calc("2 / 2"));
        assertEquals(0., calc("0 / 2"));
        assertEquals(1., calc("-2 / -2"));
        assertEquals(-1., calc("2 / -2"));
        assertEquals(-1, calc("-50.5 / 50.5"));
        assertEquals(-123., calc("123 / -1"));
        assertEquals(2., Math.round(calc("10 / 50") * 10));
        assertEquals(2500., calc("-100.0 / 20/ -2/0.001"));
    }

    @Test
    void calculateExponentiation() {
        assertEquals(4., calc("2 ^ 2"));
        assertEquals(0., calc("0 ^ 2"));
        assertEquals(1., calc("2 ^ 0"));
        assertEquals(.25, calc("2 ^ -2"));
        assertEquals(4., Math.round(calc("-0.2 ^ 2") * 100));
        assertEquals(16., calc("-2 ^2^2"));
    }

    @Test
    void calculateBrackets() {
        assertEquals(3.5, calc("3 + (4 * 2) / (1 - 5)^2"));
        assertEquals(200., calc("5*5*(4-2)^3"));
        assertEquals(170., calc("10*(2+15)"));
        assertEquals(5., calc("(6+10-4)/(1+1*2)+1"));
        assertEquals(-5., calc("-(2+3)"));
        assertEquals(-9., calc("-(2+3*2)-1"));
    }

    @Test
    void calculateFactorial() {
        assertEquals(120., calc("5!"));
        assertEquals(130., calc("5!+10"));
        assertEquals(120., calc("(3+2)!"));
        assertEquals(120., calc("(2.5*2)!"));
        assertEquals(360., calc("3*5!"));
        assertEquals(5., calc("3+2!"));
        assertEquals(43200., calc("3*5!^2"));
        assertEquals(129600., calc("(3*5!)^2"));
        assertEquals(-129600., calc("-(3*5!)^2"));
    }
}