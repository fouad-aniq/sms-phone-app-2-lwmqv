package ai.shreds.domain.value_objects; 
  
 import lombok.Value; 
 import java.io.Serializable; 
  
 /** 
  * DomainValueDeliveryDetails is a value object that holds detailed information 
  * about the delivery of a processed message, including response codes, 
  * delivery receipts, and failure reasons. 
  */ 
 @Value 
 public class DomainValueDeliveryDetails implements Serializable { 
     /** 
      * The response code provided by the SMS Gateway or external provider 
      * indicating the result of the delivery attempt. 
      */ 
     String providerResponseCode; 
  
     /** 
      * Detailed information about the delivery receipt, such as timestamps, 
      * status messages, or additional metadata. 
      */ 
     String deliveryReceiptDetails; 
  
     /** 
      * The reason for delivery failure, if the message was not delivered successfully. 
      */ 
     String failureReason; 
 } 
 