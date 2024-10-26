package ai.shreds.domain.services; 
  
 import org.slf4j.Logger; 
 import org.slf4j.LoggerFactory; 
  
 public class DomainServiceErrorHandling { 
  
     private static final Logger logger = LoggerFactory.getLogger(DomainServiceErrorHandling.class); 
  
     public void handleError(Exception exception, Object context) { 
         // Log the error 
         logError(exception); 
  
         // Determine if the operation should be retried 
         if (shouldRetry(exception)) { 
             // Throw a custom RetryableException 
             throw new RetryableException("Retryable exception occurred", exception); 
         } else { 
             // Throw a custom NonRetryableException 
             throw new NonRetryableException("Non-retryable exception occurred", exception); 
         } 
     } 
  
     public void logError(Exception exception) { 
         // Log the exception details 
         logger.error("An error occurred: {}", exception.getMessage(), exception); 
     } 
  
     public boolean shouldRetry(Exception exception) { 
         // Determine if the exception is transient and the operation should be retried 
         if (exception instanceof java.io.IOException) { 
             // IOExceptions are considered retryable 
             return true; 
         } else if (exception instanceof java.net.SocketTimeoutException) { 
             // Network timeouts are retryable 
             return true; 
         } else { 
             // Other exceptions are not retryable 
             return false; 
         } 
     } 
  
     // Custom exceptions defined within the same class 
     public static class RetryableException extends RuntimeException { 
         public RetryableException(String message, Throwable cause) { 
             super(message, cause); 
         } 
     } 
  
     public static class NonRetryableException extends RuntimeException { 
         public NonRetryableException(String message, Throwable cause) { 
             super(message, cause); 
         } 
     } 
  
 } 
 