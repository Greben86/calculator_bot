package spring.bot.calculator.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum MessageType {
    TEXT("text"),
    PICTURE("picture"),
    VIDEO("video"),
    FILE("file"),
    LOCATION("location"),
    CONTACT("contact"),
    STICKER("sticker"),
    URL("url");

    @Getter
    @JsonValue
    private final String typeName;

    @JsonCreator
    public static MessageType getTypeFromName(String value) {
        for (MessageType messageType : values()) {
            if (value.equals(messageType.typeName)) {
                return messageType;
            }
        }
        throw new IllegalArgumentException("Illegal creation type: " + value);
    }
}
