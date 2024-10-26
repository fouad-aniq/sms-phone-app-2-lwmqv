package ai.shreds.application.ports; 
  
 import ai.shreds.shared.SharedScheduleDto; 
 import ai.shreds.shared.SharedValidationResponse; 
  
 public interface ApplicationValidateScheduleInputPort { 
     SharedValidationResponse validateScheduleData(SharedScheduleDto scheduleDto); 
 }