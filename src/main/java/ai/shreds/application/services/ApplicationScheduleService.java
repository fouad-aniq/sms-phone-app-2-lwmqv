package ai.shreds.application.services;

import ai.shreds.application.ports.*;
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
        DomainScheduleEntity schedule = scheduleMapper.toDomain(scheduleDto);
        scheduleValidationServicePort.validateSchedule(schedule);
        boolean isDuplicate = scheduleRepositoryPort.existsByMessageContentAndRecipientAndScheduledTime(
                schedule.getMessageContent(), schedule.getRecipient(), schedule.getScheduledTime());
        if (isDuplicate) {
            throw new DomainInvalidScheduleException("Duplicate schedule detected.");
        }
        scheduleRepositoryPort.save(schedule);
        cacheSyncPort.syncScheduleToCache(schedule);
        return scheduleMapper.toDto(schedule);
    }

    @Override
    public SharedScheduleDto updateSchedule(UUID scheduleId, SharedScheduleDto scheduleDto) {
        DomainScheduleEntity existingSchedule = scheduleRepositoryPort.findById(scheduleId);
        if (existingSchedule == null) {
            throw new DomainInvalidScheduleException("Schedule not found.");
        }
        existingSchedule.setMessageContent(scheduleDto.getMessageContent());
        existingSchedule.setRecipient(scheduleDto.getRecipient());
        existingSchedule.setScheduledTime(scheduleDto.getScheduledTime());
        existingSchedule.setStatus(scheduleDto.getStatus());
        scheduleValidationServicePort.validateSchedule(existingSchedule);
        boolean isDuplicate = scheduleRepositoryPort.existsByMessageContentAndRecipientAndScheduledTimeExcludingId(
                existingSchedule.getMessageContent(), existingSchedule.getRecipient(), existingSchedule.getScheduledTime(), scheduleId);
        if (isDuplicate) {
            throw new DomainInvalidScheduleException("Duplicate schedule detected.");
        }
        scheduleRepositoryPort.save(existingSchedule);
        cacheSyncPort.syncScheduleToCache(existingSchedule);
        return scheduleMapper.toDto(existingSchedule);
    }

    @Override
    public SharedScheduleResponse deleteSchedule(UUID scheduleId) {
        if (!scheduleRepositoryPort.existsById(scheduleId)) {
            throw new DomainInvalidScheduleException("Schedule not found.");
        }
        scheduleRepositoryPort.deleteById(scheduleId);
        cacheSyncPort.removeScheduleFromCache(scheduleId);
        return new SharedScheduleResponse("Schedule deleted successfully.", scheduleId);
    }

    @Override
    public SharedValidationResponse validateScheduleData(SharedScheduleDto scheduleDto) {
        DomainScheduleEntity schedule = scheduleMapper.toDomain(scheduleDto);
        try {
            scheduleValidationServicePort.validateSchedule(schedule);
            return new SharedValidationResponse(true, Collections.emptyList());
        } catch (DomainInvalidScheduleException e) {
            return new SharedValidationResponse(false, Collections.singletonList(e.getMessage()));
        }
    }
}