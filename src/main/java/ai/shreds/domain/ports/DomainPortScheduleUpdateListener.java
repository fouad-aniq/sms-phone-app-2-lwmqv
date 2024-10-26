package ai.shreds.domain.ports; 
  
 import ai.shreds.domain.entities.DomainEntitySchedule; 
 import java.util.UUID; 
  
 /** 
  * Interface defining operations for handling incoming schedule updates. 
  * This interface allows the domain layer to react to schedule additions, updates, and removals, 
  * ensuring that the monitoring system reflects the most current schedule data. 
  */ 
 public interface DomainPortScheduleUpdateListener { 
  
     /** 
      * Handles the addition of new schedules, ensuring they are monitored appropriately. 
      *  
      * @param schedule the schedule that has been added 
      */ 
     void onScheduleAdded(DomainEntitySchedule schedule); 
  
     /** 
      * Handles updates to existing schedules, adjusting monitoring parameters as necessary. 
      *  
      * @param schedule the schedule that has been updated 
      */ 
     void onScheduleUpdated(DomainEntitySchedule schedule); 
  
     /** 
      * Handles the removal of schedules, stopping their monitoring. 
      *  
      * @param scheduleId the UUID of the schedule that has been removed 
      */ 
     void onScheduleRemoved(UUID scheduleId); 
 } 
 