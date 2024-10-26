package ai.shreds.domain.ports; 
  
 import ai.shreds.domain.entities.DomainScheduleEntity; 
 import ai.shreds.domain.exceptions.DomainInvalidScheduleException; 
  
 public interface DomainScheduleValidationServicePort { 
  
     void validateSchedule(DomainScheduleEntity schedule) throws DomainInvalidScheduleException; 
  
 }