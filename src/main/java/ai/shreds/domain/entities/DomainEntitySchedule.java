package ai.shreds.domain.entities; 
  
 import java.util.UUID; 
 import java.sql.Timestamp; 
 import lombok.Data; 
 import lombok.Builder; 
 import lombok.NoArgsConstructor; 
 import lombok.AllArgsConstructor; 
  
 import ai.shreds.shared.SharedValueScheduleStatus; 
  
 @Data 
 @Builder 
 @NoArgsConstructor 
 @AllArgsConstructor 
 public class DomainEntitySchedule { 
  
     private UUID scheduleId; 
     private String messageContent; 
     private String recipient; 
     private Timestamp scheduledTime; 
     private SharedValueScheduleStatus status; 
     private Timestamp createdAt; 
     private Timestamp updatedAt; 
  
     public boolean isDue(Timestamp currentTime) { 
         if (status == null || scheduledTime == null || currentTime == null) { 
             return false; 
         } 
         return status == SharedValueScheduleStatus.ACTIVE && !scheduledTime.after(currentTime); 
     } 
 } 
 