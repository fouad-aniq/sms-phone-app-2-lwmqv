package ai.shreds.shared; 
  
 import lombok.Data; 
 import com.fasterxml.jackson.annotation.JsonFormat; 
 import com.fasterxml.jackson.annotation.JsonProperty; 
 import javax.validation.constraints.NotNull; 
 import java.time.LocalDateTime; 
  
 @Data 
 public class SharedDeliveryConfirmationDTO { 
     @NotNull 
     @JsonProperty("messageId") 
     private String messageId; 
  
     @NotNull 
     @JsonProperty("gatewayMessageId") 
     private String gatewayMessageId; 
  
     @NotNull 
     @JsonProperty("deliveryStatus") 
     private String deliveryStatus; 
  
     @NotNull 
     @JsonProperty("deliveryTimestamp") 
     @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") 
     private LocalDateTime deliveryTimestamp; 
  
     @JsonProperty("details") 
     private String details; 
 } 
 