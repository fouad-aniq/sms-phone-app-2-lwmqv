package ai.shreds.infrastructure.exceptions; 
  
 public class InfrastructureExceptionRedisConnection extends RuntimeException { 
  
     private static final long serialVersionUID = 1L; 
  
     public InfrastructureExceptionRedisConnection(String message) { 
         super(message); 
     } 
  
     public InfrastructureExceptionRedisConnection(String message, Throwable cause) { 
         super(message, cause); 
     } 
  
     public InfrastructureExceptionRedisConnection(Throwable cause) { 
         super(cause); 
     } 
 } 
 