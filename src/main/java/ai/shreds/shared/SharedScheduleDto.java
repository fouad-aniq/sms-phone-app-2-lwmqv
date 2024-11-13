package ai.shreds.shared;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.UUID;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import javax.validation.Payload;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SharedScheduleDto {

    private UUID scheduleId;

    @NotBlank(message = "messageContent must not be null or empty")
    @Size(max = 65535, message = "messageContent exceeds maximum length")
    private String messageContent;

    @NotBlank(message = "recipient must not be null or empty")
    @Pattern(
        regexp = "^(\\+?[0-9]{7,15})$|^(.+)@(.+)$",
        message = "recipient must be a valid email or phone number"
    )
    private String recipient;

    @NotBlank(message = "scheduledTime must not be null or empty")
    @Pattern(
        regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}(?:\\:\\d{2}(?:\\.\\d{1,9})?)?(?:[+-]\\d{2}:\\d{2}|Z)$",
        message = "scheduledTime must be in ISO 8601 format"
    )
    @FutureTimestamp(message = "scheduledTime must be a future timestamp")
    private String scheduledTime;

    @Pattern(
        regexp = "active|paused|deleted",
        message = "status must be one of the allowed values: active, paused, deleted"
    )
    private String status = "active";

    @Pattern(
        regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}(?:\\:\\d{2}(?:\\.\\d{1,9})?)?(?:[+-]\\d{2}:\\d{2}|Z)$",
        message = "createdAt must be in ISO 8601 format"
    )
    private String createdAt;

    @Pattern(
        regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}(?:\\:\\d{2}(?:\\.\\d{1,9})?)?(?:[+-]\\d{2}:\\d{2}|Z)$",
        message = "updatedAt must be in ISO 8601 format"
    )
    private String updatedAt;
}

@Constraint(validatedBy = FutureTimestampValidator.class)
@interface FutureTimestamp {
    String message() default "scheduledTime must be a future timestamp";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

class FutureTimestampValidator implements ConstraintValidator<FutureTimestamp, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Null values are handled by @NotBlank
        }
        try {
            Instant instant = Instant.parse(value);
            return instant.isAfter(Instant.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}