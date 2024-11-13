package ai.shreds.infrastructure.mappers;

import ai.shreds.domain.entities.DomainScheduleEntity;
import ai.shreds.infrastructure.entities.InfrastructureScheduleEntity;
import ai.shreds.infrastructure.entities.InfrastructureCacheScheduleEntity;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class InfrastructureScheduleMapper {

    public InfrastructureScheduleEntity toDatabaseEntity(DomainScheduleEntity schedule) {
        if (schedule == null) {
            return null;
        }
        InfrastructureScheduleEntity databaseEntity = new InfrastructureScheduleEntity();
        databaseEntity.setScheduleId(schedule.getScheduleId());
        databaseEntity.setMessageContent(schedule.getMessageContent());
        databaseEntity.setRecipient(schedule.getRecipient());
        databaseEntity.setScheduledTime(Timestamp.valueOf(schedule.getScheduledTime()));
        databaseEntity.setStatus(schedule.getStatus());
        databaseEntity.setCreatedAt(Timestamp.valueOf(schedule.getCreatedAt()));
        databaseEntity.setUpdatedAt(Timestamp.valueOf(schedule.getUpdatedAt()));
        return databaseEntity;
    }

    public DomainScheduleEntity toDomainEntityFromDatabase(InfrastructureScheduleEntity databaseEntity) {
        if (databaseEntity == null) {
            return null;
        }
        DomainScheduleEntity schedule = new DomainScheduleEntity();
        schedule.setScheduleId(databaseEntity.getScheduleId());
        schedule.setMessageContent(databaseEntity.getMessageContent());
        schedule.setRecipient(databaseEntity.getRecipient());
        schedule.setScheduledTime(databaseEntity.getScheduledTime().toLocalDateTime());
        schedule.setStatus(databaseEntity.getStatus());
        schedule.setCreatedAt(databaseEntity.getCreatedAt().toLocalDateTime());
        schedule.setUpdatedAt(databaseEntity.getUpdatedAt().toLocalDateTime());
        return schedule;
    }

    public InfrastructureCacheScheduleEntity toCacheEntity(DomainScheduleEntity schedule) {
        if (schedule == null) {
            return null;
        }
        InfrastructureCacheScheduleEntity cacheEntity = new InfrastructureCacheScheduleEntity();
        cacheEntity.setScheduleId(schedule.getScheduleId());
        cacheEntity.setMessageContent(schedule.getMessageContent());
        cacheEntity.setRecipient(schedule.getRecipient());
        cacheEntity.setScheduledTime(Timestamp.valueOf(schedule.getScheduledTime()));
        cacheEntity.setStatus(schedule.getStatus());
        cacheEntity.setCreatedAt(Timestamp.valueOf(schedule.getCreatedAt()));
        cacheEntity.setUpdatedAt(Timestamp.valueOf(schedule.getUpdatedAt()));
        return cacheEntity;
    }

    public DomainScheduleEntity toDomainEntityFromCache(InfrastructureCacheScheduleEntity cacheEntity) {
        if (cacheEntity == null) {
            return null;
        }
        DomainScheduleEntity schedule = new DomainScheduleEntity();
        schedule.setScheduleId(cacheEntity.getScheduleId());
        schedule.setMessageContent(cacheEntity.getMessageContent());
        schedule.setRecipient(cacheEntity.getRecipient());
        schedule.setScheduledTime(cacheEntity.getScheduledTime().toLocalDateTime());
        schedule.setStatus(cacheEntity.getStatus());
        schedule.setCreatedAt(cacheEntity.getCreatedAt().toLocalDateTime());
        schedule.setUpdatedAt(cacheEntity.getUpdatedAt().toLocalDateTime());
        return schedule;
    }
}