package ai.shreds.shared; 
  
 import lombok.Data; 
 import lombok.Builder; 
 import lombok.NoArgsConstructor; 
 import lombok.AllArgsConstructor; 
 import java.util.Date; 
  
 @Data 
 @Builder 
 @NoArgsConstructor 
 @AllArgsConstructor 
 public class SharedDeliveryDetailsDTO { 
  
     private String deliveryStatus; 
     private Date deliveryTimestamp; 
     private String providerResponse; 
     private String errorCode; 
     private String errorMessage; 
  
 }