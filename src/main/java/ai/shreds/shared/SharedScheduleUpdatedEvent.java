package ai.shreds.shared; 
  
 import lombok.Data; 
 import java.util.UUID; 
 import java.sql.Timestamp; 
  
 @Data 
 public class SharedScheduleUpdatedEvent { 
     private UUID scheduleId; 
     private String messageContent; 
     private String recipient; 
     private Timestamp scheduledTime; 
     private String status; 
     private Timestamp createdAt; 
     private Timestamp updatedAt; 
 } 
 