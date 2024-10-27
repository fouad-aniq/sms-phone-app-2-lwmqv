package ai.shreds.shared; 
  
 import lombok.Data; 
 import lombok.NoArgsConstructor; 
 import lombok.AllArgsConstructor; 
 import lombok.Builder; 
  
 import java.util.Date; 
  
 import ai.shreds.shared.SharedEnumDispatchStatus; 
  
 /** 
  * DTO representing the response received after dispatching a message. 
  * Contains information about the dispatch status and delivery details. 
  */ 
 @Data 
 @NoArgsConstructor 
 @AllArgsConstructor 
 @Builder 
 public class SharedDispatchResponseDTO { 
     /** 
      * Unique identifier of the dispatched message. 
      */ 
     private String messageId; 
  
     /** 
      * Status of the message dispatch (e.g., 'PENDING', 'SENT', 'DELIVERED', 'FAILED', 'RETRYING'). 
      */ 
     private SharedEnumDispatchStatus dispatchStatus; 
      
     /** 
      * Timestamp when the message was delivered. 
      */ 
     private Date deliveryTimestamp; 
      
     /** 
      * Additional details about the dispatch or delivery. 
      */ 
     private String details; 
 } 
 