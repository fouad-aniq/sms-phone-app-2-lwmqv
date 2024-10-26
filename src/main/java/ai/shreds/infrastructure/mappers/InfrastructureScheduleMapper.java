package ai.shreds.infrastructure.mappers; 
  
 import ai.shreds.domain.entities.DomainScheduleEntity; 
 import ai.shreds.infrastructure.entities.InfrastructureScheduleEntity; 
 import ai.shreds.infrastructure.entities.InfrastructureCacheScheduleEntity; 
 import org.mapstruct.Mapper; 
  
 @Mapper(componentModel = "spring") 
 public interface InfrastructureScheduleMapper { 
  
     InfrastructureScheduleEntity toDatabaseEntity(DomainScheduleEntity schedule); 
  
     DomainScheduleEntity toDomainEntityFromDatabase(InfrastructureScheduleEntity databaseEntity); 
  
     InfrastructureCacheScheduleEntity toCacheEntity(DomainScheduleEntity schedule); 
  
     DomainScheduleEntity toDomainEntityFromCache(InfrastructureCacheScheduleEntity cacheEntity); 
  
 } 
 