package ai.shreds.infrastructure.exceptions; 
  
 public class InfrastructureCacheAccessException extends Exception { 
      
     private final String message; 
      
     public InfrastructureCacheAccessException(String message) { 
         super(message); 
         this.message = message; 
     } 
      
     public String getMessage() { 
         return message; 
     } 
 } 
 