package ai.shreds.domain.value_objects;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

public class DomainValueMetadataValue {

    private final Map<String, String> metadata;

    public DomainValueMetadataValue(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public boolean isValid() {
        if (metadata == null) {
            return true; // No metadata to validate
        }

        // Validate 'timestamp' if present
        if (metadata.containsKey("timestamp")) {
            String timestamp = metadata.get("timestamp");
            if (!isValidTimestamp(timestamp)) {
                return false;
            }
        }

        // Validate 'priority' if present
        if (metadata.containsKey("priority")) {
            String priorityStr = metadata.get("priority");
            if (!isValidPriority(priorityStr)) {
                return false;
            }
        }

        // Validate 'senderId' if present
        if (metadata.containsKey("senderId")) {
            String senderId = metadata.get("senderId");
            if (!isValidSenderId(senderId)) {
                return false;
            }
        }

        // All validations passed
        return true;
    }

    private boolean isValidTimestamp(String timestamp) {
        try {
            DateTimeFormatter.ISO_DATE_TIME.parse(timestamp);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean isValidPriority(String priorityStr) {
        try {
            int priority = Integer.parseInt(priorityStr);
            return priority >= 1 && priority <= 5;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidSenderId(String senderId) {
        return senderId != null && !senderId.trim().isEmpty() && senderId.length() <= 50;
    }
}
