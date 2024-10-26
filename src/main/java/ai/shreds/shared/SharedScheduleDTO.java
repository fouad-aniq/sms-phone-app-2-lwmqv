package ai.shreds.shared; 
  
 import lombok.Data; 
 import lombok.AllArgsConstructor; 
 import lombok.NoArgsConstructor; 
 import java.util.UUID; 
 import java.sql.Timestamp; 
 import ai.shreds.shared.SharedValueScheduleStatus; 
  
 @Data 
 @AllArgsConstructor 
 @NoArgsConstructor 
 public class SharedScheduleDTO { 
     private UUID scheduleId; 
     private String messageContent; 
     private String recipient; 
     private Timestamp scheduledTime; 
     private SharedValueScheduleStatus status; 
     private Timestamp createdAt; 
     private Timestamp updatedAt; 
 } 
 