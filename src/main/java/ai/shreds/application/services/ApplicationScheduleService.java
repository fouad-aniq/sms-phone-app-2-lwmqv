package ai.shreds.application.services; 
  
 import ai.shreds.application.ports.ApplicationCacheSyncPort; 
 import ai.shreds.application.ports.ApplicationCreateScheduleInputPort; 
 import ai.shreds.application.ports.ApplicationDeleteScheduleInputPort; 
 import ai.shreds.application.ports.ApplicationUpdateScheduleInputPort; 
 import ai.shreds.application.ports.ApplicationValidateScheduleInputPort; 
 import ai.shreds.application.utils.ApplicationScheduleMapper; 
 import ai.shreds.domain.entities.DomainScheduleEntity; 
 import ai.shreds.domain.exceptions.DomainInvalidScheduleException; 
 import ai.shreds.domain.ports.DomainScheduleRepositoryPort; 
 import ai.shreds.domain.ports.DomainScheduleValidationServicePort; 
 import ai.shreds.shared.SharedScheduleDto; 
 import ai.shreds.shared.SharedScheduleResponse; 
 import ai.shreds.shared.SharedValidationResponse; 
  
 import lombok.RequiredArgsConstructor; 
  
 import java.util.Collections; 
 import java.util.UUID; 
  
 @RequiredArgsConstructor 
 public class ApplicationScheduleService implements ApplicationCreateScheduleInputPort, 
         ApplicationUpdateScheduleInputPort, 
         ApplicationDeleteScheduleInputPort, 
         ApplicationValidateScheduleInputPort { 
  
     private final DomainScheduleRepositoryPort scheduleRepositoryPort; 
     private final DomainScheduleValidationServicePort scheduleValidationServicePort; 
     private final ApplicationCacheSyncPort cacheSyncService; 
     private final ApplicationScheduleMapper scheduleMapper; 
  
     @Override 
     public SharedScheduleDto createSchedule(SharedScheduleDto scheduleDto) { 
         // Convert DTO to domain entity 
         DomainScheduleEntity scheduleEntity = scheduleMapper.toDomain(scheduleDto); 
  
         // Validate schedule 
         scheduleValidationServicePort.validateSchedule(scheduleEntity); 
  
         // Save to repository 
         scheduleRepositoryPort.save(scheduleEntity); 
  
         // Sync to cache 
         cacheSyncService.syncScheduleToCache(scheduleEntity); 
  
         // Convert back to DTO 
         return scheduleMapper.toDto(scheduleEntity); 
     } 
  
     @Override 
     public SharedScheduleDto updateSchedule(UUID scheduleId, SharedScheduleDto scheduleDto) { 
         // Check if schedule exists 
         if (!scheduleRepositoryPort.existsById(scheduleId)) { 
             throw new IllegalArgumentException("Schedule with ID " + scheduleId + " does not exist."); 
         } 
  
         // Convert DTO to domain entity 
         DomainScheduleEntity scheduleEntity = scheduleMapper.toDomain(scheduleDto); 
         scheduleEntity.setScheduleId(scheduleId); 
  
         // Validate schedule 
         scheduleValidationServicePort.validateSchedule(scheduleEntity); 
  
         // Update in repository 
         scheduleRepositoryPort.save(scheduleEntity); 
  
         // Sync to cache 
         cacheSyncService.syncScheduleToCache(scheduleEntity); 
  
         // Convert back to DTO 
         return scheduleMapper.toDto(scheduleEntity); 
     } 
  
     @Override 
     public SharedScheduleResponse deleteSchedule(UUID scheduleId) { 
         // Check if schedule exists 
         if (!scheduleRepositoryPort.existsById(scheduleId)) { 
             throw new IllegalArgumentException("Schedule with ID " + scheduleId + " does not exist."); 
         } 
  
         // Delete from repository 
         scheduleRepositoryPort.deleteById(scheduleId); 
  
         // Remove from cache 
         cacheSyncService.removeScheduleFromCache(scheduleId); 
  
         // Create response 
         SharedScheduleResponse response = new SharedScheduleResponse(); 
         response.setMessage("Schedule deleted successfully."); 
         response.setScheduleId(scheduleId); 
         return response; 
     } 
  
     @Override 
     public SharedValidationResponse validateScheduleData(SharedScheduleDto scheduleDto) { 
         // Convert DTO to domain entity 
         DomainScheduleEntity scheduleEntity = scheduleMapper.toDomain(scheduleDto); 
  
         SharedValidationResponse validationResponse = new SharedValidationResponse(); 
         try { 
             // Validate schedule 
             scheduleValidationServicePort.validateSchedule(scheduleEntity); 
             validationResponse.setValid(true); 
             validationResponse.setErrors(Collections.emptyList()); 
         } catch (DomainInvalidScheduleException e) { 
             validationResponse.setValid(false); 
             validationResponse.setErrors(Collections.singletonList(e.getMessage())); 
         } 
         return validationResponse; 
     } 
 } 
 