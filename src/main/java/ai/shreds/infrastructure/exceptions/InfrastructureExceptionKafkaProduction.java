package ai.shreds.infrastructure.exceptions; 
  
 public class InfrastructureExceptionKafkaProduction extends RuntimeException { 
  
     private static final long serialVersionUID = 1L; 
  
     public InfrastructureExceptionKafkaProduction(String message) { 
         super(message); 
     } 
  
     public InfrastructureExceptionKafkaProduction(String message, Throwable cause) { 
         super(message, cause); 
     } 
 } 
 