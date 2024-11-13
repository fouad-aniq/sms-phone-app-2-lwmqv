package ai.shreds.domain.services;

import ai.shreds.domain.entities.DomainScheduleEntity;
import ai.shreds.domain.exceptions.DomainInvalidScheduleException;
import ai.shreds.domain.ports.DomainScheduleValidationServicePort;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.regex.Pattern;

public class DomainScheduleValidationService implements DomainScheduleValidationServicePort {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE);

    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^\\+?[1-9]\\d{1,14}$");

    @Override
    public void validateSchedule(DomainScheduleEntity schedule) throws DomainInvalidScheduleException {
        StringBuilder errors = new StringBuilder();

        if (schedule.getMessageContent() == null || schedule.getMessageContent().trim().isEmpty()) {
            errors.append("messageContent is mandatory and cannot be empty.\n");
        }

        if (schedule.getRecipient() == null || schedule.getRecipient().trim().isEmpty()) {
            errors.append("recipient is mandatory and cannot be empty.\n");
        } else {
            String recipient = schedule.getRecipient().trim();
            if (!isValidEmail(recipient) && !isValidPhoneNumber(recipient)) {
                errors.append("recipient must be a valid email or phone number.\n");
            }
        }

        if (schedule.getScheduledTime() == null) {
            errors.append("scheduledTime is mandatory and must be provided.\n");
        } else {
            Timestamp now = Timestamp.from(Instant.now());
            if (!schedule.getScheduledTime().after(now)) {
                errors.append("scheduledTime must be a future timestamp.\n");
            }
        }

        if (schedule.getStatus() != null) {
            String status = schedule.getStatus().trim().toLowerCase();
            if (!status.equals("active") && !status.equals("paused") && !status.equals("deleted")) {
                errors.append("status must be one of 'active', 'paused', or 'deleted'.\n");
            }
        }

        if (errors.length() > 0) {
            throw new DomainInvalidScheduleException(errors.toString().trim());
        }
    }

    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private boolean isValidPhoneNumber(String phone) {
        return PHONE_PATTERN.matcher(phone).matches();
    }

}
