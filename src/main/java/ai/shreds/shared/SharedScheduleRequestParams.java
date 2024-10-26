package ai.shreds.shared; 
  
 import lombok.Data; 
 import lombok.NoArgsConstructor; 
 import javax.validation.constraints.NotEmpty; 
 import javax.validation.constraints.NotNull; 
 import javax.validation.constraints.Pattern; 
  
 @Data 
 @NoArgsConstructor 
 public class SharedScheduleRequestParams { 
  
     @NotNull(message = "Message content must not be null") 
     @NotEmpty(message = "Message content must not be empty") 
     private String messageContent; 
  
     @NotNull(message = "Recipient must not be null") 
     @NotEmpty(message = "Recipient must not be empty") 
     private String recipient; 
  
     @NotNull(message = "Scheduled time must not be null") 
     @NotEmpty(message = "Scheduled time must not be empty") 
     private String scheduledTime; 
  
     @Pattern(regexp = "active|paused|deleted", message = "Status must be one of 'active', 'paused', or 'deleted'") 
     private String status; 
  
 }