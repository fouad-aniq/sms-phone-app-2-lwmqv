package ai.shreds.infrastructure.exceptions; 
  
 public class InfrastructureDataAccessException extends Exception { 
  
     public InfrastructureDataAccessException() { 
         super(); 
     } 
  
     public InfrastructureDataAccessException(String message) { 
         super(message); 
     } 
  
     public InfrastructureDataAccessException(String message, Throwable cause) { 
         super(message, cause); 
     } 
  
     public InfrastructureDataAccessException(Throwable cause) { 
         super(cause); 
     } 
 } 
 