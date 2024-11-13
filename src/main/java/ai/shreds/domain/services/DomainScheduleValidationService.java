package ai.shreds.domain.services;

import ai.shreds.domain.entities.DomainScheduleEntity;
import ai.shreds.domain.exceptions.DomainInvalidScheduleException;
import ai.shreds.domain.ports.DomainScheduleValidationServicePort;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

public class DomainScheduleValidationService implements DomainScheduleValidationServicePort {

    private static final List<String> VALID_STATUSES = Arrays.asList("active", "paused", "deleted");

    @Override
    public void validateSchedule(DomainScheduleEntity schedule) throws DomainInvalidScheduleException {
        if (schedule == null) {
            throw new DomainInvalidScheduleException("Schedule cannot be null.");
        }

        OffsetDateTime scheduledTimeStamp = schedule.getScheduledTime();
        if (scheduledTimeStamp == null) {
            throw new DomainInvalidScheduleException("Scheduled time cannot be null.");
        }
        Instant scheduledInstant = scheduledTimeStamp.toInstant();
        Instant nowInstant = Instant.now();
        if (!scheduledInstant.isAfter(nowInstant)) {
            throw new DomainInvalidScheduleException("Scheduled time must be in the future.");
        }

        String status = schedule.getStatus();
        if (status == null || status.trim().isEmpty()) {
            throw new DomainInvalidScheduleException("Status cannot be null or empty.");
        }
        if (!VALID_STATUSES.contains(status.toLowerCase())) {
            throw new DomainInvalidScheduleException("Invalid status value.");
        }

        String messageContent = schedule.getMessageContent();
        if (messageContent == null || messageContent.trim().isEmpty()) {
            throw new DomainInvalidScheduleException("Message content cannot be null or empty.");
        }

        String recipient = schedule.getRecipient();
        if (recipient == null || recipient.trim().isEmpty()) {
            throw new DomainInvalidScheduleException("Recipient cannot be null or empty.");
        }
        if (!recipient.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}")) {
            throw new DomainInvalidScheduleException("Recipient must be a valid email.");
        }
    }
}