package ai.shreds.application.utils;

import ai.shreds.shared.SharedScheduleDto;
import ai.shreds.domain.entities.DomainScheduleEntity;
import ai.shreds.domain.exceptions.DomainInvalidScheduleException;
import lombok.extern.slf4j.Slf4j;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
public class ApplicationScheduleMapper {

    private static final List<String> VALID_STATUSES = Arrays.asList("active", "paused", "deleted");

    public DomainScheduleEntity toDomain(SharedScheduleDto scheduleDto) throws DomainInvalidScheduleException {
        if (scheduleDto == null) {
            log.error("ScheduleDto cannot be null");
            throw new DomainInvalidScheduleException("ScheduleDto cannot be null");
        }

        validateMandatoryFields(scheduleDto);
        validateStatus(scheduleDto.getStatus());

        Timestamp scheduledTime = parseTimestamp(scheduleDto.getScheduledTime(), "scheduledTime");

        Timestamp createdAt = scheduleDto.getCreatedAt() != null && !scheduleDto.getCreatedAt().isEmpty() ? parseTimestamp(scheduleDto.getCreatedAt(), "createdAt") : new Timestamp(System.currentTimeMillis());

        Timestamp updatedAt = scheduleDto.getUpdatedAt() != null && !scheduleDto.getUpdatedAt().isEmpty() ? parseTimestamp(scheduleDto.getUpdatedAt(), "updatedAt") : new Timestamp(System.currentTimeMillis());

        UUID scheduleId = scheduleDto.getScheduleId() != null ? scheduleDto.getScheduleId() : UUID.randomUUID();
        String status = scheduleDto.getStatus() != null ? scheduleDto.getStatus().toLowerCase() : "active";

        DomainScheduleEntity schedule = new DomainScheduleEntity();
        schedule.setScheduleId(scheduleId);
        schedule.setMessageContent(scheduleDto.getMessageContent());
        schedule.setRecipient(scheduleDto.getRecipient());
        schedule.setScheduledTime(scheduledTime);
        schedule.setStatus(status);
        schedule.setCreatedAt(createdAt);
        schedule.setUpdatedAt(updatedAt);

        return schedule;
    }

    public SharedScheduleDto toDto(DomainScheduleEntity schedule) {
        if (schedule == null) {
            log.error("DomainScheduleEntity cannot be null");
            return null;
        }

        SharedScheduleDto scheduleDto = new SharedScheduleDto();
        scheduleDto.setScheduleId(schedule.getScheduleId());
        scheduleDto.setMessageContent(schedule.getMessageContent());
        scheduleDto.setRecipient(schedule.getRecipient());
        scheduleDto.setScheduledTime(formatTimestamp(schedule.getScheduledTime()));
        scheduleDto.setStatus(schedule.getStatus());
        scheduleDto.setCreatedAt(formatTimestamp(schedule.getCreatedAt()));
        scheduleDto.setUpdatedAt(formatTimestamp(schedule.getUpdatedAt()));

        return scheduleDto;
    }

    private void validateMandatoryFields(SharedScheduleDto scheduleDto) throws DomainInvalidScheduleException {
        if (scheduleDto.getMessageContent() == null || scheduleDto.getMessageContent().isEmpty()) {
            log.error("Message content is required");
            throw new DomainInvalidScheduleException("Message content is required");
        }

        if (scheduleDto.getRecipient() == null || scheduleDto.getRecipient().isEmpty()) {
            log.error("Recipient is required");
            throw new DomainInvalidScheduleException("Recipient is required");
        }

        if (scheduleDto.getScheduledTime() == null || scheduleDto.getScheduledTime().isEmpty()) {
            log.error("Scheduled time is required");
            throw new DomainInvalidScheduleException("Scheduled time is required");
        }
    }

    private void validateStatus(String status) throws DomainInvalidScheduleException {
        if (status != null && !VALID_STATUSES.contains(status.toLowerCase())) {
            log.error("Invalid status value: {}", status);
            throw new DomainInvalidScheduleException("Invalid status value: " + status);
        }
    }

    private Timestamp parseTimestamp(String dateTimeStr, String fieldName) throws DomainInvalidScheduleException {
        try {
            Instant instant = Instant.parse(dateTimeStr);
            Timestamp timestamp = Timestamp.from(instant);
            if ("scheduledTime".equals(fieldName) && timestamp.before(new Timestamp(System.currentTimeMillis()))) {
                log.error("{} must be in the future", fieldName);
                throw new DomainInvalidScheduleException(fieldName + " must be in the future");
            }
            return timestamp;
        } catch (DateTimeParseException e) {
            log.error("Invalid date-time format for {}: {}", fieldName, dateTimeStr);
            throw new DomainInvalidScheduleException("Invalid date-time format for " + fieldName + ": " + dateTimeStr);
        }
    }

    private String formatTimestamp(Timestamp timestamp) {
        if (timestamp != null) {
            return timestamp.toInstant().toString();
        } else {
            return null;
        }
    }
}