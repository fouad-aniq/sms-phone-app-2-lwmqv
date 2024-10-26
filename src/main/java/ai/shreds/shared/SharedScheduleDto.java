package ai.shreds.shared; 
  
 import lombok.Data; 
 import lombok.NoArgsConstructor; 
 import lombok.AllArgsConstructor; 
 import lombok.Builder; 
  
 import javax.validation.constraints.NotEmpty; 
 import javax.validation.constraints.NotNull; 
 import java.util.UUID; 
  
 @Data 
 @NoArgsConstructor 
 @AllArgsConstructor 
 @Builder 
 public class SharedScheduleDto { 
  
     private UUID scheduleId; 
  
     @NotNull 
     @NotEmpty 
     private String messageContent; 
  
     @NotNull 
     @NotEmpty 
     private String recipient; 
  
     @NotNull 
     @NotEmpty 
     private String scheduledTime; // ISO 8601 format 
  
     private String status; 
  
     private String createdAt; // ISO 8601 format 
  
     private String updatedAt; // ISO 8601 format 
  
 } 
 