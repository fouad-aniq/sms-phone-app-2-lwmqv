package ai.shreds.infrastructure.repositories.mapper;

import ai.shreds.domain.entities.DomainEntitySchedule;
import ai.shreds.domain.entities.ScheduleEntity;
import ai.shreds.shared.SharedValueScheduleStatus;

public class ScheduleMapper {

    public DomainEntitySchedule toDomainEntity(ScheduleEntity scheduleEntity) {
        if (scheduleEntity == null) {
            return null;
        }
        return DomainEntitySchedule.builder()
                .scheduleId(scheduleEntity.getScheduleId())
                .messageContent(scheduleEntity.getMessageContent())
                .recipient(scheduleEntity.getRecipient())
                .scheduledTime(scheduleEntity.getScheduledTime())
                .status(scheduleEntity.getStatus())
                .createdAt(scheduleEntity.getCreatedAt())
                .updatedAt(scheduleEntity.getUpdatedAt())
                .build();
    }

    public ScheduleEntity toEntity(DomainEntitySchedule schedule) {
        if (schedule == null) {
            return null;
        }
        return ScheduleEntity.builder()
                .scheduleId(schedule.getScheduleId())
                .messageContent(schedule.getMessageContent())
                .recipient(schedule.getRecipient())
                .scheduledTime(schedule.getScheduledTime())
                .status(schedule.getStatus())
                .createdAt(schedule.getCreatedAt())
                .updatedAt(schedule.getUpdatedAt())
                .build();
    }
}