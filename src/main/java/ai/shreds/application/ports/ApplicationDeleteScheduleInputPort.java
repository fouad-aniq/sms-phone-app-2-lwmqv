package ai.shreds.application.ports; 
  
 import java.util.UUID; 
 import ai.shreds.shared.SharedScheduleResponse; 
  
 /** 
  * Interface representing the input port for deleting a schedule. 
  * Implements the ApplicationDeleteScheduleInputPort in the Hexagonal Architecture. 
  */ 
 public interface ApplicationDeleteScheduleInputPort { 
  
     /** 
      * Deletes an existing schedule identified by the given scheduleId. 
      * 
      * @param scheduleId the UUID of the schedule to delete 
      * @return a SharedScheduleResponse containing a confirmation message and the ID of the deleted schedule 
      */ 
     SharedScheduleResponse deleteSchedule(UUID scheduleId); 
 } 
 