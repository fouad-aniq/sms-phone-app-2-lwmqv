package ai.shreds.shared; 
  
 import lombok.Data; 
 import lombok.NoArgsConstructor; 
 import lombok.AllArgsConstructor; 
 import com.fasterxml.jackson.annotation.JsonProperty; 
  
 @Data 
 @NoArgsConstructor 
 @AllArgsConstructor 
 public class SharedErrorNotificationResponse { 
      
     @JsonProperty("status") 
     private String status; 
      
     @JsonProperty("messageId") 
     private String messageId; 
      
 }