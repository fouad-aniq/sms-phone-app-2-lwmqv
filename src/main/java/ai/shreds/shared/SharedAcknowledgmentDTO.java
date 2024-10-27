package ai.shreds.shared; 
  
 import lombok.Data; 
 import lombok.Builder; 
 import lombok.NoArgsConstructor; 
 import lombok.AllArgsConstructor; 
  
 import java.util.Date; 
 import java.io.Serializable; 
  
 @Data 
 @Builder 
 @NoArgsConstructor 
 @AllArgsConstructor 
 public class SharedAcknowledgmentDTO implements Serializable { 
     private static final long serialVersionUID = 1L; 
      
     private String messageId; 
     private String dispatchStatus; 
     private Date deliveryTimestamp; 
     private String details; 
 }