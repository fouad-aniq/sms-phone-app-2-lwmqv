package ai.shreds.domain.value_objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DomainValueMetadataValue {
    private Map<String, String> metadata;

    public boolean isValid() {
        if (metadata == null || metadata.isEmpty()) {
            return true; // No metadata to validate
        }

        for (Map.Entry<String, String> entry : metadata.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if ("timestamp".equalsIgnoreCase(key)) {
                // Validate that timestamp is in ISO 8601 format
                try {
                    ZonedDateTime.parse(value, DateTimeFormatter.ISO_DATE_TIME);
                } catch (DateTimeParseException e) {
                    return false; // Invalid timestamp format
                }
            } else if ("priority".equalsIgnoreCase(key)) {
                // Validate that priority is one of 'high', 'normal', or 'low'
                if (!"high".equalsIgnoreCase(value) && !"normal".equalsIgnoreCase(value) && !"low".equalsIgnoreCase(value)) {
                    return false; // Invalid priority value
                }
            } else if ("senderID".equalsIgnoreCase(key)) {
                // No specific validation required for senderID
                // Accept as is
            }
            // Additional metadata fields can be validated here if needed
        }

        return true; // All validations passed
    }
}
