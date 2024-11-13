package ai.shreds.shared;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SharedScheduleRequestParams {

    @NotEmpty(message = "Message content must not be empty.")
    private String messageContent;

    @NotEmpty(message = "Recipient must not be empty.")
    @Pattern(
        regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$|^\\S+@\\S+\\.\\S+$",
        message = "Recipient must be a valid email address or phone number."
    )
    private String recipient;

    @NotEmpty(message = "Scheduled time must not be empty.")
    private String scheduledTime;

    @Pattern(
        regexp = "^(active|paused|deleted)$",
        message = "Status must be 'active', 'paused', or 'deleted'."
    )
    private String status;

    @AssertTrue(message = "Scheduled time must be in ISO 8601 format and represent a future time.")
    private boolean isScheduledTimeValid() {
        if (scheduledTime == null || scheduledTime.isEmpty()) {
            return false;
        }
        try {
            LocalDateTime dateTime = LocalDateTime.parse(scheduledTime, DateTimeFormatter.ISO_DATE_TIME);
            return dateTime.isAfter(LocalDateTime.now());
        } catch (DateTimeParseException ex) {
            return false;
        }
    }
}
