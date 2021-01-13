package spring.bot.calculator.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EventTypes {
    WEBHOOK("webhook"),
    SUBSCRIBED("subscribed"),
    UNSUBSCRIBED("unsubscribed"),
    DELIVERED("delivered"),
    MESSAGE("message"),
    SEEN("seen"),
    CONVERSATION_STARTED("conversation_started");

    @Getter
    @JsonValue
    private final String typeName;

    @JsonCreator
    public static EventTypes getTypeFromName(String value) {
        for (EventTypes eventType : values()) {
            if (value.equals(eventType.typeName)) {
                return eventType;
            }
        }
        throw new IllegalArgumentException("Illegal creation type: " + value);
    }
}
