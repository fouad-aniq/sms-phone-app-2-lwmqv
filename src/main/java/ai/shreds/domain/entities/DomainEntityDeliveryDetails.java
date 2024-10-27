package ai.shreds.domain.entities; 
  
 import java.util.Date; 
 import lombok.Getter; 
 import lombok.Setter; 
 import lombok.Builder; 
 import lombok.EqualsAndHashCode; 
 import lombok.ToString; 
 import lombok.NoArgsConstructor; 
 import lombok.AllArgsConstructor; 
  
 /** 
  * Represents detailed information about the delivery status of a dispatched message. 
  */ 
 @Getter 
 @Setter 
 @Builder 
 @NoArgsConstructor 
 @AllArgsConstructor 
 @EqualsAndHashCode 
 @ToString 
 public class DomainEntityDeliveryDetails { 
  
     /** 
      * Unique identifier for the delivery details entry. 
      */ 
     private String id; 
  
     /** 
      * Reference to the associated ProcessedMessage's id. 
      */ 
     private String messageId; 
  
     /** 
      * Status of delivery (e.g., 'delivered', 'failed'). 
      */ 
     private String deliveryStatus; 
  
     /** 
      * Timestamp when the message was delivered. 
      */ 
     private Date deliveryTimestamp; 
  
     /** 
      * Response from the SMS Gateway provider. 
      */ 
     private String providerResponse; 
  
     /** 
      * Error code if delivery failed. 
      */ 
     private String errorCode; 
  
     /** 
      * Detailed error message if delivery failed. 
      */ 
     private String errorMessage; 
  
 } 
 