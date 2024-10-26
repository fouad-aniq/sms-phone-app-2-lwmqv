package ai.shreds.domain.services; 
  
 import ai.shreds.domain.entities.DomainScheduleEntity; 
 import ai.shreds.domain.exceptions.DomainInvalidScheduleException; 
 import ai.shreds.domain.ports.DomainScheduleValidationServicePort; 
  
 import java.sql.Timestamp; 
 import java.time.Instant; 
 import java.util.Arrays; 
 import java.util.List; 
  
 public class DomainScheduleValidationService implements DomainScheduleValidationServicePort { 
  
     private static final List<String> VALID_STATUSES = Arrays.asList("active", "paused", "deleted"); 
  
     @Override 
     public void validateSchedule(DomainScheduleEntity schedule) throws DomainInvalidScheduleException { 
         if (schedule == null) { 
             throw new DomainInvalidScheduleException("Schedule cannot be null."); 
         } 
  
         // Validate scheduledTime 
         Timestamp scheduledTimeStamp = schedule.getScheduledTime(); 
         if (scheduledTimeStamp == null) { 
             throw new DomainInvalidScheduleException("Scheduled time cannot be null."); 
         } 
         Instant scheduledInstant = scheduledTimeStamp.toInstant(); 
         Instant nowInstant = Instant.now(); 
         if (!scheduledInstant.isAfter(nowInstant)) { 
             throw new DomainInvalidScheduleException("Scheduled time must be in the future."); 
         } 
  
         // Validate status 
         String status = schedule.getStatus(); 
         if (status == null || status.trim().isEmpty()) { 
             throw new DomainInvalidScheduleException("Status cannot be null or empty."); 
         } 
         if (!VALID_STATUSES.contains(status.toLowerCase())) { 
             throw new DomainInvalidScheduleException("Invalid status value."); 
         } 
  
         // Validate messageContent 
         String messageContent = schedule.getMessageContent(); 
         if (messageContent == null || messageContent.trim().isEmpty()) { 
             throw new DomainInvalidScheduleException("Message content cannot be null or empty."); 
         } 
  
         // Validate recipient 
         String recipient = schedule.getRecipient(); 
         if (recipient == null || recipient.trim().isEmpty()) { 
             throw new DomainInvalidScheduleException("Recipient cannot be null or empty."); 
         } 
         // Optionally, validate recipient format (e.g., email or phone number) 
     } 
 } 
 