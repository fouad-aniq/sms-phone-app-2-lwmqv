package ai.shreds.shared; 
  
 import java.util.List; 
 import com.fasterxml.jackson.annotation.JsonInclude; 
 import lombok.Data; 
 import lombok.NoArgsConstructor; 
 import lombok.AllArgsConstructor; 
  
 @Data 
 @NoArgsConstructor 
 @AllArgsConstructor 
 @JsonInclude(JsonInclude.Include.NON_NULL) 
 public class SharedValidationResponse { 
  
     private boolean valid; 
     private List<String> errors; 
  
 }