package ai.shreds.shared;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.AssertTrue;
import java.util.Map;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SharedSMSMessageDTO {

    @NotNull(message = "messageId cannot be null")
    private String messageId;

    @NotNull(message = "recipientNumber cannot be null")
    @Pattern(regexp = "^\\+\\d{1,15}$", message = "Invalid recipient number format")
    private String recipientNumber;

    @NotNull(message = "content cannot be null")
    @Size(max = 160, message = "Content must not exceed 160 characters")
    private String content;

    private Map<String, String> metadata;

    @AssertTrue(message = "Content contains prohibited words")
    private boolean isContentValid() {
        List<String> prohibitedWords = List.of("prohibitedWord1", "prohibitedWord2"); // Example prohibited words
        if (content == null) {
            return true; // NotNull validation will handle null
        }
        for (String word : prohibitedWords) {
            if (content.toLowerCase().contains(word.toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    @AssertTrue(message = "Invalid metadata")
    private boolean isMetadataValid() {
        if (metadata == null || metadata.isEmpty()) {
            return true;
        }
        // Validate 'timestamp' if present in metadata
        if (metadata.containsKey("timestamp")) {
            String timestamp = metadata.get("timestamp");
            try {
                DateTimeFormatter.ISO_DATE_TIME.parse(timestamp);
            } catch (DateTimeParseException e) {
                return false;
            }
        }
        // Add additional metadata validation if needed
        return true;
    }
}
