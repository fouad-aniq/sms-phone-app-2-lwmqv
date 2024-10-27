package ai.shreds.domain.entities; 
  
 import org.springframework.data.annotation.Id; 
 import org.springframework.data.mongodb.core.mapping.Document; 
 import org.springframework.data.annotation.CreatedDate; 
 import org.springframework.data.annotation.LastModifiedDate; 
  
 import lombok.Data; 
 import lombok.NoArgsConstructor; 
 import lombok.AllArgsConstructor; 
 import lombok.Builder; 
  
 import ai.shreds.shared.SharedEnumDeliveryStatus; 
 import ai.shreds.domain.value_objects.DomainValueDeliveryDetails; 
  
 import java.time.LocalDateTime; 
 import java.util.List; 
  
 @Data 
 @NoArgsConstructor 
 @AllArgsConstructor 
 @Builder 
 @Document(collection = "processed_messages") 
 public class DomainEntityProcessedMessage { 
  
     @Id 
     private String id; 
  
     private String originalMessageId; 
     private String personalizedContent; 
     private Boolean validationStatus; 
     private List<String> businessRulesApplied; 
     private Boolean preparedForDispatch; 
     private SharedEnumDeliveryStatus dispatchStatus; 
     private LocalDateTime dispatchTimestamp; 
     private DomainValueDeliveryDetails deliveryDetails; 
     private Integer retryCount; 
  
     @CreatedDate 
     private LocalDateTime createdAt; 
  
     @LastModifiedDate 
     private LocalDateTime updatedAt; 
 }