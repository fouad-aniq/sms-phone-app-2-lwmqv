package ai.shreds.shared; 
  
 import lombok.AllArgsConstructor; 
 import lombok.Builder; 
 import lombok.Data; 
 import lombok.NoArgsConstructor; 
  
 import java.util.UUID; 
  
 @Data 
 @NoArgsConstructor 
 @AllArgsConstructor 
 @Builder 
 public class SharedScheduleResponse { 
     private String message; 
     private UUID scheduleId; 
 } 
 