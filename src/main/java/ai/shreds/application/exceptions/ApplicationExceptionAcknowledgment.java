package ai.shreds.application.exceptions; 
  
 public class ApplicationExceptionAcknowledgment extends RuntimeException { 
      
     public ApplicationExceptionAcknowledgment() { 
         super(); 
     } 
      
     public ApplicationExceptionAcknowledgment(String message) { 
         super(message); 
     } 
      
     public ApplicationExceptionAcknowledgment(String message, Throwable cause) { 
         super(message, cause); 
     } 
      
     public ApplicationExceptionAcknowledgment(Throwable cause) { 
         super(cause); 
     } 
 }