package ai.shreds.shared; 
  
 import lombok.Data; 
 import lombok.Builder; 
 import lombok.NoArgsConstructor; 
 import lombok.AllArgsConstructor; 
  
 /** 
  * Data Transfer Object for Error Notifications received from SMS Gateway Integration Service. 
  */ 
 @Data 
 @Builder 
 @NoArgsConstructor 
 @AllArgsConstructor 
 public class SharedErrorNotificationDTO { 
     private String messageId; 
     private SharedEnumDispatchStatus dispatchStatus; 
     private String errorCode; 
     private String errorMessage; 
 } 
 