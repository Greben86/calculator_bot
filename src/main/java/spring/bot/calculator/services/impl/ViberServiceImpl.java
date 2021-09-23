package spring.bot.calculator.services.impl;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import spring.bot.calculator.config.ViberConfig;
import spring.bot.calculator.model.EventTypes;
import spring.bot.calculator.model.MessageType;
import spring.bot.calculator.model.ViberMessageIn;
import spring.bot.calculator.model.ViberMessageOut;
import spring.bot.calculator.services.ViberService;

import java.util.Arrays;
import java.util.List;

@Service
public class ViberServiceImpl implements ViberService {
    private static final String TOKEN_HEADER_NAME = "X-Viber-Auth-Token";

    @Autowired
    private ViberConfig viberConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CalculateServiceImpl calculateService;

    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add(TOKEN_HEADER_NAME, viberConfig.getBotToken());
        return httpHeaders;
    }

    private ResponseEntity<String> sentMessage(String receiverId, String message) {
        ViberMessageOut viberMessageOut = new ViberMessageOut();
        viberMessageOut.setReceiver(receiverId);
        viberMessageOut.setType(MessageType.TEXT);
        viberMessageOut.setText(message);

        HttpEntity<ViberMessageOut> entity = new HttpEntity<>(viberMessageOut, getHeaders());
        return restTemplate.exchange(viberConfig.getSendMessageUrl(), HttpMethod.POST, entity, String.class);
    }

    @Override
    public ResponseEntity<String> setWebhook() {
        String jsonString = new JSONObject()
                .put("url", viberConfig.getBotUrl())
                .put("event_types", getSupportedEventTypes())
                .toString();

        HttpEntity<String> entity = new HttpEntity<>(jsonString, getHeaders());
        return restTemplate.exchange(viberConfig.getSetWebhookUrl(), HttpMethod.POST, entity, String.class);
    }

    @Override
    public ResponseEntity<String> botProcess(ViberMessageIn message) {
        if (EventTypes.WEBHOOK.equals(message.getEvent())) {
            String jsonString = new JSONObject()
                    .put("status", 0)
                    .put("status_message", "ok")
                    .put("event_types", getSupportedEventTypes())
                    .toString();

            return new ResponseEntity<>(jsonString, HttpStatus.OK);
        } else
        if (EventTypes.MESSAGE.equals(message.getEvent())) {
            String result = message.getMessage().getText();
            result = result + " = " + calculateService.calculate(result);
            return sentMessage(message.getSender().getId(), result);
        } else
        if (EventTypes.UNSUBSCRIBED.equals(message.getEvent())) {
            return sentMessage(message.getSender().getId(), "Unsubscribed");
        }
        if (EventTypes.CONVERSATION_STARTED.equals(message.getEvent())) {
            return sentMessage(message.getSender().getId(), "Welcome");
        }
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    private List<EventTypes> getSupportedEventTypes() {
        return Arrays.asList(EventTypes.UNSUBSCRIBED, EventTypes.MESSAGE, EventTypes.CONVERSATION_STARTED);
    }

}