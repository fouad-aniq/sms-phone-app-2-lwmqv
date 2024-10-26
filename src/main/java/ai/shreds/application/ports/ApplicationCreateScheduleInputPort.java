package ai.shreds.application.ports; 
  
 import ai.shreds.shared.SharedScheduleDto; 
  
 public interface ApplicationCreateScheduleInputPort { 
     SharedScheduleDto createSchedule(SharedScheduleDto scheduleDto); 
 } 
 