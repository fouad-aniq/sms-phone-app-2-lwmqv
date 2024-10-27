package ai.shreds.infrastructure.utils; 
  
 import ai.shreds.shared.SharedUtilRetryPolicy; 
 import org.springframework.beans.factory.annotation.Value; 
 import org.springframework.stereotype.Component; 
 import java.time.Duration; 
  
 @Component 
 public class InfrastructureUtilRetryPolicy { 
  
     @Value("${retry.maxAttempts:3}") 
     private Integer maxAttempts; 
  
     @Value("${retry.waitIntervalMillis:1000}") 
     private Long waitIntervalMillis; 
  
     @Value("${retry.backoffMultiplier:2.0}") 
     private Double backoffMultiplier; 
  
     public SharedUtilRetryPolicy getRetryPolicy() { 
         SharedUtilRetryPolicy retryPolicy = new SharedUtilRetryPolicy(); 
         retryPolicy.setMaxAttempts(maxAttempts); 
         retryPolicy.setWaitInterval(Duration.ofMillis(waitIntervalMillis)); 
         retryPolicy.setBackoffMultiplier(backoffMultiplier); 
         return retryPolicy; 
     } 
 } 
 