package ai.shreds.shared; 
  
 import lombok.AllArgsConstructor; 
 import lombok.Builder; 
 import lombok.Data; 
 import lombok.NoArgsConstructor; 
 import com.fasterxml.jackson.annotation.JsonFormat; 
 import com.fasterxml.jackson.annotation.JsonInclude; 
 import javax.validation.constraints.NotNull; 
 import javax.validation.constraints.Size; 
 import java.util.Date; 
 import java.util.Map; 
  
 /** 
  * Data Transfer Object for Message Request. 
  */ 
 @Data 
 @Builder 
 @NoArgsConstructor 
 @AllArgsConstructor 
 @JsonInclude(JsonInclude.Include.NON_NULL) 
 public class SharedMessageRequestDTO { 
      
     /** 
      * Unique identifier for the message. 
      */ 
     @NotNull 
     private String id; 
      
     /** 
      * The text content of the message. 
      */ 
     @NotNull 
     @Size(max = 160) 
     private String content; 
      
     /** 
      * The phone number or identifier of the message recipient. 
      */ 
     @NotNull 
     private String recipient; 
      
     /** 
      * The time when the message was created. 
      */ 
     @NotNull 
     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "UTC") 
     private Date timestamp; 
      
     /** 
      * Priority level of the message (e.g., 'HIGH', 'MEDIUM', 'LOW'). 
      */ 
     @NotNull 
     private String priorityLevel; 
      
     /** 
      * Additional custom attributes related to the message. 
      */ 
     private Map<String, Object> metadata; 
 } 
 