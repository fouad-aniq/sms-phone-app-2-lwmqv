package ai.shreds.shared; 
  
 import lombok.Data; 
 import lombok.Builder; 
 import lombok.NoArgsConstructor; 
 import lombok.AllArgsConstructor; 
  
 /** 
  * Represents a business rule applied to messages during processing to modify or enrich them. 
  */ 
 @Data 
 @Builder 
 @NoArgsConstructor 
 @AllArgsConstructor 
 public class SharedBusinessRule { 
  
     /** 
      * Unique identifier for the business rule. 
      */ 
     private String id; 
  
     /** 
      * Name of the business rule. 
      */ 
     private String name; 
  
     /** 
      * Description of the business rule. 
      */ 
     private String description; 
  
     /** 
      * The logic or script that defines how the rule modifies the message. 
      */ 
     private String logic; 
  
     /** 
      * Indicates if the business rule is currently active. 
      */ 
     private Boolean isActive; 
  
 }