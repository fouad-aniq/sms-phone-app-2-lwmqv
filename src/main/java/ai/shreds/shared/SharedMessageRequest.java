package ai.shreds.shared; 
  
 import lombok.AllArgsConstructor; 
 import lombok.Builder; 
 import lombok.Data; 
 import lombok.EqualsAndHashCode; 
 import lombok.NoArgsConstructor; 
  
 import javax.validation.constraints.NotEmpty; 
 import javax.validation.constraints.NotNull; 
 import java.io.Serializable; 
 import java.util.Date; 
 import java.util.Map; 
  
 @Data 
 @Builder 
 @NoArgsConstructor 
 @AllArgsConstructor 
 @EqualsAndHashCode 
 public class SharedMessageRequest implements Serializable { 
     @NotNull 
     private String id; 
  
     @NotEmpty 
     private String content; 
  
     @NotEmpty 
     private String recipient; 
  
     @NotNull 
     private Date timestamp; 
  
     @NotEmpty 
     private String priorityLevel; 
  
     private Map<String, Object> metadata; 
 } 
 