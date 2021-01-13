package spring.bot.calculator.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ViberMessageIn {
    private EventTypes event;
    private Sender sender;
    private Message message;
}
