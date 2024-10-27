package ai.shreds.domain.exceptions; 
  
 /** 
  * DomainExceptionMessageDispatch is a custom exception class that represents exceptions 
  * that occur during the message dispatch process in the domain layer. 
  */ 
 public class DomainExceptionMessageDispatch extends RuntimeException { 
  
     private static final long serialVersionUID = 1L; 
  
     /** 
      * Constructs a new DomainExceptionMessageDispatch with the specified detail message. 
      * 
      * @param message the detail message. 
      */ 
     public DomainExceptionMessageDispatch(String message) { 
         super(message); 
     } 
  
     /** 
      * Constructs a new DomainExceptionMessageDispatch with the specified detail message and cause. 
      * 
      * @param message the detail message. 
      * @param cause the cause of the exception. 
      */ 
     public DomainExceptionMessageDispatch(String message, Throwable cause) { 
         super(message, cause); 
     } 
 } 
 