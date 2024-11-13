package ai.shreds.application.services;

import ai.shreds.application.ports.ApplicationCreateScheduleInputPort;
import ai.shreds.application.ports.ApplicationUpdateScheduleInputPort;
import ai.shreds.application.ports.ApplicationDeleteScheduleInputPort;
import ai.shreds.application.ports.ApplicationValidateScheduleInputPort;
import ai.shreds.application.ports.ApplicationCacheSyncPort;
import ai.shreds.application.utils.ApplicationScheduleMapper;
import ai.shreds.domain.entities.DomainScheduleEntity;
import ai.shreds.domain.exceptions.DomainInvalidScheduleException;
import ai.shreds.domain.ports.DomainScheduleRepositoryPort;
import ai.shreds.domain.ports.DomainScheduleValidationServicePort;
import ai.shreds.shared.SharedScheduleDto;
import ai.shreds.shared.SharedScheduleResponse;
import ai.shreds.shared.SharedValidationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicationScheduleService implements ApplicationCreateScheduleInputPort,
        ApplicationUpdateScheduleInputPort, ApplicationDeleteScheduleInputPort,
        ApplicationValidateScheduleInputPort {

    private final DomainScheduleRepositoryPort scheduleRepositoryPort;
    private final DomainScheduleValidationServicePort scheduleValidationServicePort;
    private final ApplicationCacheSyncPort cacheSyncPort;
    private final ApplicationScheduleMapper scheduleMapper;

    @Override
    public SharedScheduleDto createSchedule(SharedScheduleDto scheduleDto) {
        // Convert DTO to domain entity
        DomainScheduleEntity schedule = scheduleMapper.toDomain(scheduleDto);

        // Validate schedule
        scheduleValidationServicePort.validateSchedule(schedule);

        // Check for duplicates
        boolean isDuplicate = scheduleRepositoryPort.existsByMessageContentAndRecipientAndScheduledTime(
                schedule.getMessageContent(), schedule.getRecipient(), schedule.getScheduledTime());
        if (isDuplicate) {
            throw new DomainInvalidScheduleException("Duplicate schedule detected.");
        }

        // Save schedule
        scheduleRepositoryPort.save(schedule);

        // Synchronize cache
        cacheSyncPort.syncScheduleToCache(schedule);

        // Return DTO
        return scheduleMapper.toDto(schedule);
    }

    @Override
    public SharedScheduleDto updateSchedule(UUID scheduleId, SharedScheduleDto scheduleDto) {
        // Retrieve existing schedule
        DomainScheduleEntity existingSchedule = scheduleRepositoryPort.findById(scheduleId);
        if (existingSchedule == null) {
            throw new DomainInvalidScheduleException("Schedule not found.");
        }

        // Update fields
        existingSchedule.setMessageContent(scheduleDto.getMessageContent());
        existingSchedule.setRecipient(scheduleDto.getRecipient());
        existingSchedule.setScheduledTime(scheduleDto.getScheduledTime());
        existingSchedule.setStatus(scheduleDto.getStatus());

        // Validate updated schedule
        scheduleValidationServicePort.validateSchedule(existingSchedule);

        // Check for duplicates (excluding current schedule)
        boolean isDuplicate = scheduleRepositoryPort.existsByMessageContentAndRecipientAndScheduledTimeExcludingId(
                existingSchedule.getMessageContent(), existingSchedule.getRecipient(), existingSchedule.getScheduledTime(), scheduleId);
        if (isDuplicate) {
            throw new DomainInvalidScheduleException("Duplicate schedule detected.");
        }

        // Save updated schedule
        scheduleRepositoryPort.save(existingSchedule);

        // Synchronize cache
        cacheSyncPort.syncScheduleToCache(existingSchedule);

        // Return DTO
        return scheduleMapper.toDto(existingSchedule);
    }

    @Override
    public SharedScheduleResponse deleteSchedule(UUID scheduleId) {
        // Check if schedule exists
        if (!scheduleRepositoryPort.existsById(scheduleId)) {
            throw new DomainInvalidScheduleException("Schedule not found.");
        }

        // Delete schedule
        scheduleRepositoryPort.deleteById(scheduleId);

        // Remove from cache
        cacheSyncPort.removeScheduleFromCache(scheduleId);

        // Return response
        return new SharedScheduleResponse("Schedule deleted successfully.", scheduleId);
    }

    @Override
    public SharedValidationResponse validateScheduleData(SharedScheduleDto scheduleDto) {
        DomainScheduleEntity schedule = scheduleMapper.toDomain(scheduleDto);
        try {
            // Validate schedule
            scheduleValidationServicePort.validateSchedule(schedule);
            return new SharedValidationResponse(true, Collections.emptyList());
        } catch (DomainInvalidScheduleException e) {
            return new SharedValidationResponse(false, Collections.singletonList(e.getMessage()));
        }
    }
}
