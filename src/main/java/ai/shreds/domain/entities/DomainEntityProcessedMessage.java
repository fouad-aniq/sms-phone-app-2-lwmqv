package ai.shreds.domain.entities; 
  
 import java.util.Date; 
 import java.util.List; 
 import lombok.Data; 
 import lombok.Builder; 
 import lombok.NoArgsConstructor; 
 import lombok.AllArgsConstructor; 
  
 @Data 
 @Builder 
 @NoArgsConstructor 
 @AllArgsConstructor 
 public class DomainEntityProcessedMessage { 
     private String id; 
     private String originalMessageId; 
     private String personalizedContent; 
     private Boolean validationStatus; 
     private List<String> businessRulesApplied; 
     private Boolean preparedForDispatch; 
     private Date timestamp; 
 } 
 