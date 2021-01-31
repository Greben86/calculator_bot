package spring.bot.calculator.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.bot.calculator.component.ExpressionManager;
import spring.bot.calculator.services.CalculateService;

import java.text.DecimalFormat;

@Service
public class CalculateServiceImpl implements CalculateService {

    private DecimalFormat formatter = new DecimalFormat("#.##");

    @Autowired
    private ExpressionManager expressionManager;

    @Override
    public String calculate(String expression) {
        try {
            Double result = expressionManager.calculate(expression);
            return formatter.format(result);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
