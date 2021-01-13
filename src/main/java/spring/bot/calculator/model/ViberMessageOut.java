package spring.bot.calculator.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ViberMessageOut {
    private String receiver;
    private MessageType type;
    private Sender sender;
    private String text;
}
