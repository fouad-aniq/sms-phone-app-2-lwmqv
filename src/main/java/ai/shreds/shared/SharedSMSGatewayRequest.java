package ai.shreds.shared.dto; 
  
 import lombok.Data; 
 import lombok.NoArgsConstructor; 
 import lombok.AllArgsConstructor; 
  
 import javax.validation.constraints.NotBlank; 
 import javax.validation.constraints.Pattern; 
  
 @Data 
 @NoArgsConstructor 
 @AllArgsConstructor 
 public class SharedSMSGatewayRequest { 
  
     @NotBlank(message = "Message ID must not be blank") 
     @Pattern(regexp = "^[0-9a-fA-F]{8}\-[0-9a-fA-F]{4}\-[1-5][0-9a-fA-F]{3}\-[89abAB][0-9a-fA-F]{3}\-[0-9a-fA-F]{12}$", message = "Message ID must be a valid UUID") 
     private String messageId; 
  
     @NotBlank(message = "Recipient must not be blank") 
     @Pattern(regexp = "^\+[1-9]\d{1,14}$", message = "Recipient must be a valid E.164 formatted phone number") 
     private String recipient; 
  
     @NotBlank(message = "Content must not be blank") 
     private String content; 
 }