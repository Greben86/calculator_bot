package spring.bot.calculator.component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationInfo {
    String name();
    OperationPriority priority() default OperationPriority.MEDIUM;
}
