package ai.shreds.shared; 
  
 import lombok.AllArgsConstructor; 
 import lombok.Builder; 
 import lombok.Data; 
 import lombok.NoArgsConstructor; 
  
 @Data 
 @NoArgsConstructor 
 @AllArgsConstructor 
 @Builder 
 public class SharedMessageDispatchResponse { 
     private String status; 
     private String messageId; 
     private String details; 
 }