package ai.shreds.domain.entities; 
  
 import lombok.Data; 
 import lombok.Builder; 
 import java.util.Date; 
  
 @Data 
 @Builder 
 public class DomainEntityDeliveryDetails { 
     private String deliveryStatus; 
     private Date deliveryTimestamp; 
     private String providerResponse; 
     private String errorCode; 
     private String errorMessage; 
 }