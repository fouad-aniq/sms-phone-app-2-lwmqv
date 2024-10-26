// Package declaration 
 package ai.shreds.domain.services; 
  
 // Necessary imports 
 import ai.shreds.domain.entities.DomainEntitySchedule; 
 import ai.shreds.domain.exceptions.DomainExceptionInvalidSchedule; 
 import ai.shreds.domain.ports.DomainPortScheduleCache; 
 import ai.shreds.domain.services.DomainServiceErrorHandling; 
  
 import java.util.UUID; 
  
 // Class definition 
 public class DomainServiceScheduleUpdateListener { 
  
     private final DomainPortScheduleCache scheduleCachePort; 
     private final DomainServiceErrorHandling errorHandlingService; 
  
     // Constructor with dependency injection 
     public DomainServiceScheduleUpdateListener(DomainPortScheduleCache scheduleCachePort, 
                                                DomainServiceErrorHandling errorHandlingService) { 
         this.scheduleCachePort = scheduleCachePort; 
         this.errorHandlingService = errorHandlingService; 
     } 
  
     // Method to handle schedule addition 
     public void onScheduleAdded(DomainEntitySchedule schedule) { 
         try { 
             // Validate the incoming schedule 
             validateSchedule(schedule); 
  
             // Save the new schedule to the schedule cache 
             scheduleCachePort.saveSchedule(schedule); 
  
         } catch (Exception e) { 
             // Handle exceptions using the error handling service 
             errorHandlingService.handleError(e, schedule); 
         } 
     } 
  
     // Method to handle schedule updates 
     public void onScheduleUpdated(DomainEntitySchedule schedule) { 
         try { 
             // Validate the updated schedule 
             validateSchedule(schedule); 
  
             // Update the existing schedule in the schedule cache 
             scheduleCachePort.saveSchedule(schedule); 
  
         } catch (Exception e) { 
             // Handle exceptions using the error handling service 
             errorHandlingService.handleError(e, schedule); 
         } 
     } 
  
     // Method to handle schedule removals 
     public void onScheduleRemoved(UUID scheduleId) { 
         try { 
             // Remove the schedule from the schedule cache 
             scheduleCachePort.deleteSchedule(scheduleId); 
  
         } catch (Exception e) { 
             // Handle exceptions using the error handling service 
             errorHandlingService.handleError(e, scheduleId); 
         } 
     } 
  
     // Helper method for validating schedules 
     private void validateSchedule(DomainEntitySchedule schedule) throws DomainExceptionInvalidSchedule { 
         if (schedule == null) { 
             throw new DomainExceptionInvalidSchedule('Schedule cannot be null'); 
         } 
         if (schedule.getScheduleId() == null) { 
             throw new DomainExceptionInvalidSchedule('Schedule ID cannot be null'); 
         } 
         if (schedule.getMessageContent() == null || schedule.getMessageContent().isEmpty()) { 
             throw new DomainExceptionInvalidSchedule('Message content cannot be null or empty'); 
         } 
         if (schedule.getRecipient() == null || schedule.getRecipient().isEmpty()) { 
             throw new DomainExceptionInvalidSchedule('Recipient cannot be null or empty'); 
         } 
         if (schedule.getScheduledTime() == null) { 
             throw new DomainExceptionInvalidSchedule('Scheduled time cannot be null'); 
         } 
         if (schedule.getStatus() == null) { 
             throw new DomainExceptionInvalidSchedule('Schedule status cannot be null'); 
         } 
     } 
 } 
 