package ai.shreds.shared; 
  
 import org.slf4j.Logger; 
 import org.slf4j.LoggerFactory; 
  
 public class SharedExceptionHandler { 
  
     private static final Logger logger = LoggerFactory.getLogger(SharedExceptionHandler.class); 
  
     public void handleException(Exception e) { 
         // Log the exception details 
         logger.error("An exception occurred:", e); 
     } 
 } 
 