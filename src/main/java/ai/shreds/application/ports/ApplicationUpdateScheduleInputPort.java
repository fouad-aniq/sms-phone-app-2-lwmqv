package ai.shreds.application.ports; 
  
 import java.util.UUID; 
  
 import ai.shreds.shared.SharedScheduleDto; 
  
 public interface ApplicationUpdateScheduleInputPort { 
     SharedScheduleDto updateSchedule(UUID scheduleId, SharedScheduleDto scheduleDto); 
 } 
 