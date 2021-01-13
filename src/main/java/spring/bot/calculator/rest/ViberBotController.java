package spring.bot.calculator.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.bot.calculator.model.ViberMessageIn;
import spring.bot.calculator.services.ViberService;

@RestController
@RequestMapping("/")
public class ViberBotController {

    @Autowired
    private ViberService viberService;

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return new ResponseEntity<>("ping", HttpStatus.OK);
    }

    @GetMapping(value = "/setwebhook", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> setWebHook() {
        return viberService.setWebhook();
    }

    @PostMapping(value = "/bot", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> botProcess(@RequestBody ViberMessageIn message) {
        return viberService.botProcess(message);
    }
}
