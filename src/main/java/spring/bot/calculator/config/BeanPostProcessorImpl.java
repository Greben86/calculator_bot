package spring.bot.calculator.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import spring.bot.calculator.component.OperationInfo;
import spring.bot.calculator.component.impl.AbstractOperation;

import java.lang.reflect.Field;

@Component
public class BeanPostProcessorImpl implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        OperationInfo annotation = bean.getClass().getAnnotation(OperationInfo.class);
        if (annotation != null && bean instanceof AbstractOperation) {
            try {
                String name = annotation.name();
                int priority = annotation.priority().getPriority();
                Field signatureField = bean.getClass().getSuperclass().getDeclaredField("signature");
                Field priorityField = bean.getClass().getSuperclass().getDeclaredField("priority");
                signatureField.setAccessible(true);
                priorityField.setAccessible(true);
                signatureField.set(bean, name);
                priorityField.set(bean, priority);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
