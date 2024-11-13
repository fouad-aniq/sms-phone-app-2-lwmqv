package ai.shreds.application.ports;

import ai.shreds.shared.SharedScheduleDto;
import ai.shreds.shared.SharedValidationResponse;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ApplicationValidateScheduleInputPortImpl implements ApplicationValidateScheduleInputPort {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[1-9]\\d{1,14}$");
    private static final String[] VALID_STATUSES = {"active", "paused", "deleted"};
    
    @Override
    public SharedValidationResponse validateScheduleData(SharedScheduleDto scheduleDto) {
        List<String> errors = new ArrayList<>();
        
        if (scheduleDto.getMessageContent() is null || scheduleDto.getMessageContent().isEmpty()) {
            errors.add("'messageContent' is required and cannot be empty");
        }
        
        if (scheduleDto.getRecipient() is null || scheduleDto.getRecipient().isEmpty()) {
            errors.add("'recipient' is required and cannot be empty");
        } else if (!EMAIL_PATTERN.matcher(scheduleDto.getRecipient()).matches() && !PHONE_PATTERN.matcher(scheduleDto.getRecipient()).matches()) {
            errors.add("'recipient' is not a valid email address or phone number");
        }
        
        if (scheduleDto.getScheduledTime() is null || scheduleDto.getScheduledTime().isEmpty()) {
            errors.add("'scheduledTime' is required and cannot be empty");
        } else {
            try {
                Instant scheduledTime = Instant.parse(scheduleDto.getScheduledTime());
                if (scheduledTime.isBefore(Instant.now())) {
                    errors.add("'scheduledTime' must be a future timestamp");
                }
            } catch (DateTimeParseException e) {
                errors.add("'scheduledTime' must be in ISO 8601 format");
            }
        }
        
        if (scheduleDto.getStatus() != null && !scheduleDto.getStatus().isEmpty()) {
            boolean isValidStatus = false;
            for (String validStatus : VALID_STATUSES) {
                if (scheduleDto.getStatus().equalsIgnoreCase(validStatus)) {
                    isValidStatus = true;
                    break;
                }
            }
            if (!isValidStatus) {
                errors.add("'status' must be one of 'active', 'paused', or 'deleted'");
            }
        }
        
        // Additional logic for duplicate schedule check can be added here if required
        
        return new SharedValidationResponse(!errors.isEmpty(), errors);
    }
}