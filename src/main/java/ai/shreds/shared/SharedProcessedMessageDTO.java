package ai.shreds.shared; 
  
 import lombok.Data; 
 import lombok.Builder; 
 import lombok.NoArgsConstructor; 
 import lombok.AllArgsConstructor; 
  
 import java.util.Date; 
 import java.util.List; 
  
 import ai.shreds.shared.SharedEnumDispatchStatus; 
 import ai.shreds.shared.SharedDeliveryDetailsDTO; 
  
 @Data 
 @Builder 
 @NoArgsConstructor 
 @AllArgsConstructor 
 public class SharedProcessedMessageDTO { 
  
     private String id; 
     private String originalMessageId; 
     private String personalizedContent; 
     private Boolean validationStatus; 
     private List<String> businessRulesApplied; 
     private Boolean preparedForDispatch; 
     private SharedEnumDispatchStatus dispatchStatus; 
     private Date dispatchTimestamp; 
     private SharedDeliveryDetailsDTO deliveryDetails; 
  
 }