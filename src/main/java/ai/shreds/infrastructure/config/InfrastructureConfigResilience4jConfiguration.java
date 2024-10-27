package ai.shreds.infrastructure.config; 
  
 import org.springframework.context.annotation.Bean; 
 import org.springframework.context.annotation.Configuration; 
 import io.github.resilience4j.common.Resilience4jConfigurationProperties; 
 import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigurationProperties; 
 import io.github.resilience4j.common.retry.configuration.RetryConfigurationProperties; 
 import java.time.Duration; 
  
 @Configuration 
 public class InfrastructureConfigResilience4jConfiguration { 
  
     @Bean 
     public Resilience4jConfigurationProperties resilienceConfig() { 
         Resilience4jConfigurationProperties resilienceProperties = new Resilience4jConfigurationProperties(); 
  
         // Configure Circuit Breaker properties 
         CircuitBreakerConfigurationProperties circuitBreakerProperties = new CircuitBreakerConfigurationProperties(); 
         CircuitBreakerConfigurationProperties.InstanceProperties cbInstanceProperties = new CircuitBreakerConfigurationProperties.InstanceProperties(); 
         cbInstanceProperties.setFailureRateThreshold(50.0f); 
         cbInstanceProperties.setWaitDurationInOpenState(Duration.ofSeconds(30)); 
         circuitBreakerProperties.getInstances().put("smsGatewayCircuitBreaker", cbInstanceProperties); 
         resilienceProperties.setCircuitbreaker(circuitBreakerProperties); 
  
         // Configure Retry properties 
         RetryConfigurationProperties retryProperties = new RetryConfigurationProperties(); 
         RetryConfigurationProperties.InstanceProperties retryInstanceProperties = new RetryConfigurationProperties.InstanceProperties(); 
         retryInstanceProperties.setMaxAttempts(3); 
         retryInstanceProperties.setWaitDuration(Duration.ofSeconds(2)); 
         retryProperties.getInstances().put("smsGatewayRetry", retryInstanceProperties); 
         resilienceProperties.setRetry(retryProperties); 
  
         return resilienceProperties; 
     } 
 } 
 