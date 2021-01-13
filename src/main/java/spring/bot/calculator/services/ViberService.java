package spring.bot.calculator.services;

import org.springframework.http.ResponseEntity;
import spring.bot.calculator.model.ViberMessageIn;

public interface ViberService {

    ResponseEntity<String> setWebhook();

    ResponseEntity<String> botProcess(ViberMessageIn message);
}
