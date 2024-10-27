package ai.shreds.domain.entities; 
  
 import org.springframework.data.annotation.Id; 
 import org.springframework.data.mongodb.core.mapping.Document; 
 import lombok.Data; 
 import lombok.NoArgsConstructor; 
 import lombok.AllArgsConstructor; 
  
 @Data 
 @NoArgsConstructor 
 @AllArgsConstructor 
 @Document(collection = "Policy") 
 public class DomainEntityPolicy { 
      
     @Id 
     private String id; 
      
     private String name; 
      
     private String description; 
      
     private String criteria; 
      
     private Boolean isActive; 
 } 
 