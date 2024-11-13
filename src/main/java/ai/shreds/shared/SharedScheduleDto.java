package ai.shreds.shared;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.UUID;

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
    // TODO: Implement custom validation to ensure scheduledTime is a future timestamp
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
