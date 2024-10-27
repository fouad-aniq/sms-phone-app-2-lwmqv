package ai.shreds.domain.entities; 
  
 import lombok.Data; 
 import lombok.AllArgsConstructor; 
 import lombok.NoArgsConstructor; 
 import lombok.Builder; 
  
 import java.util.Date; 
  
 /** 
  * Represents the original message request received by the system before processing. 
  */ 
 @Data 
 @AllArgsConstructor 
 @NoArgsConstructor 
 @Builder 
 public class DomainEntityMessageRequest { 
      
     /** 
      * Unique identifier for the message request. 
      */ 
     private String id; 
      
     /** 
      * Identifier of the sender initiating the message. 
      */ 
     private String senderId; 
      
     /** 
      * Phone number of the message recipient. 
      */ 
     private String recipientNumber; 
      
     /** 
      * Original message content before processing. 
      */ 
     private String content; 
      
     /** 
      * Timestamp when the message request was received. 
      */ 
     private Date requestTimestamp; 
      
     /** 
      * Current status of the message request (e.g., 'received', 'processed'). 
      */ 
     private String status; 
 } 
 