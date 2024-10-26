package ai.shreds.infrastructure.mappers;

import ai.shreds.domain.entities.DomainScheduleEntity;
import ai.shreds.infrastructure.entities.InfrastructureScheduleEntity;
import ai.shreds.infrastructure.entities.InfrastructureCacheScheduleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.sql.Timestamp;

@Mapper(componentModel = "spring")
public interface InfrastructureScheduleMapper {

    @Mapping(target = "scheduledTime", source = "schedule.scheduledTime")
    InfrastructureScheduleEntity toDatabaseEntity(DomainScheduleEntity schedule);

    @Mapping(target = "scheduledTime", source = "databaseEntity.scheduledTime")
    DomainScheduleEntity toDomainEntityFromDatabase(InfrastructureScheduleEntity databaseEntity);

    @Mapping(target = "scheduledTime", source = "schedule.scheduledTime")
    InfrastructureCacheScheduleEntity toCacheEntity(DomainScheduleEntity schedule);

    @Mapping(target = "scheduledTime", source = "cacheEntity.scheduledTime")
    DomainScheduleEntity toDomainEntityFromCache(InfrastructureCacheScheduleEntity cacheEntity);

}