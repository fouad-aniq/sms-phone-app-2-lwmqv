package ai.shreds.domain.exceptions; 
  
 public class DomainExceptionScheduleNotFound extends RuntimeException { 
  
     private final String message; 
  
     public DomainExceptionScheduleNotFound(String message) { 
         super(message); 
         this.message = message; 
     } 
  
     public DomainExceptionScheduleNotFound(String message, Throwable cause) { 
         super(message, cause); 
         this.message = message; 
     } 
  
     @Override 
     public String getMessage() { 
         return message; 
     } 
 } 
 