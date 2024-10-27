package ai.shreds.shared; 
  
 import lombok.Data; 
 import lombok.NoArgsConstructor; 
 import lombok.AllArgsConstructor; 
 import lombok.Builder; 
  
 import javax.validation.constraints.NotBlank; 
 import javax.validation.constraints.NotNull; 
  
 import com.fasterxml.jackson.annotation.JsonFormat; 
 import java.time.OffsetDateTime; 
  
 @Data 
 @NoArgsConstructor 
 @AllArgsConstructor 
 @Builder 
 public class SharedErrorNotificationDTO { 
      
     @NotBlank(message = "messageId must not be blank") 
     private String messageId; 
      
     @NotBlank(message = "gatewayMessageId must not be blank") 
     private String gatewayMessageId; 
      
     @NotBlank(message = "errorCode must not be blank") 
     private String errorCode; 
      
     @NotBlank(message = "errorMessage must not be blank") 
     private String errorMessage; 
      
     @NotNull(message = "timestamp must not be null") 
     @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX") 
     private OffsetDateTime timestamp; 
 }