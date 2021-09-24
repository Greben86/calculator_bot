package spring.bot.calculator.config;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import spring.bot.calculator.component.OperationInfo;
import spring.bot.calculator.component.impl.AbstractOperation;

@Component
public class BeanPostProcessorImpl implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        OperationInfo annotation = bean.getClass().getAnnotation(OperationInfo.class);
        if (annotation != null && bean instanceof AbstractOperation) {
            try {
                String name = annotation.name();
                int priority = annotation.priority().getPriority();
                FieldUtils.writeField(bean, "signature", name, true);
                FieldUtils.writeField(bean, "priority", priority, true);
            } catch (IllegalAccessException e) {
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
