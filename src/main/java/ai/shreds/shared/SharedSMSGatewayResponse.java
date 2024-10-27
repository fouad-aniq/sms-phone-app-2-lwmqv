package ai.shreds.shared; 
  
 import lombok.Data; 
 import lombok.NoArgsConstructor; 
 import lombok.AllArgsConstructor; 
  
 @Data 
 @NoArgsConstructor 
 @AllArgsConstructor 
 public class SharedSMSGatewayResponse { 
     private String status; 
     private String messageId; 
     private String details; 
 } 
 