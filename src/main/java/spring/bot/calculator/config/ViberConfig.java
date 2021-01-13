package spring.bot.calculator.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value= {"classpath:application.properties"})
@Getter
public class ViberConfig {
    @Value("${viber.bot.token}")
    private String botToken;

    @Value("${viber.bot.url}")
    private String botUrl;

    @Value("${viber.set_webhook.url}")
    private String setWebhookUrl;

    @Value("${viber.account_info.url}")
    private String accountInfoUrl;

    @Value("${viber.send_message.url}")
    private String sendMessageUrl;
}
