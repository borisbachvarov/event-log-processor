package org.example.validator;

import org.example.model.Event;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;


public class EventValidator {
    private static final Set<String> VALID_ACTIONS = Set.of("login", "logout", "view", "click", "purchase");

    public boolean isValid(Event event, int lineNumber) {
        if(event == null) return false;

        if (isBlank(event.getUserId()))    return invalid(lineNumber, "userId is missing or empty");
        if (isBlank(event.getEventId()))    return invalid(lineNumber, "eventId is missing");
        if (isBlank(event.getTimestamp()))    return invalid(lineNumber, "timestamp is missing");
        if (isBlank(event.getAction()))    return invalid(lineNumber, "action is missing");

        if (isValidUUID(event.getEventId()))    return invalid(lineNumber, "eventId is not a valid UUID");
        if (isValidUUID(event.getUserId()))    return invalid(lineNumber, "userId is not a valid UUID");
        if (isValidUUID(event.getTimestamp()))    return invalid(lineNumber, "timestamp is not valid ISO-8601");

        if (!isValidTimestamp(event.getTimestamp())) return invalid(lineNumber, "timestamp is not valid ISO-8601");

        if (!VALID_ACTIONS.contains(event.getAction())) return invalid(lineNumber, "unknown action: " + event.getAction());

        switch(event.getAction()) {
            case "view":
                if (isBlank(event.getArticleId())) return invalid(lineNumber, "view event missing articleId");
                break;
                case "click":
                    if (isBlank(event.getTarget())) return invalid(lineNumber, "click event missing target");
                    break;
                    case "purchase":
                        if (event.getAmount() == null || event.getAmount() < 0) return invalid(lineNumber, "purchase event has invalid amount");
        }

        return true;
    }

    private boolean invalid(int lineNumber, String reason) {
        System.err.println("Line " + lineNumber + " invalid: " + reason);
        return false;
    }

    private boolean isBlank(String string) {
        return string == null || string.trim().isEmpty();
    }

    private boolean isValidUUID(String uuid) {
        try{
            UUID.fromString(uuid);
            return false;
        } catch (IllegalArgumentException e) {
            return true;
        }
    }

    private boolean isValidTimestamp(String timestamp) {
        try {
            Instant.parse(timestamp);
            return true;
        } catch (Exception e) {
            System.err.println("DEBUG timestamp value: [" + timestamp + "] error: " + e.getMessage());
            return false;
        }
    }
}
