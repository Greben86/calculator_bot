package spring.bot.calculator.component;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OperationPriority {
    LOW(3), MEDIUM(2), HIGH(1);

    private final int priority;
}
