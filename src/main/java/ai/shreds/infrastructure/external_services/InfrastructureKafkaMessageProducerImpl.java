package ai.shreds.infrastructure.external_services; 
  
 import ai.shreds.domain.entities.DomainEntityMessageProcessingEvent; 
 import ai.shreds.domain.ports.DomainPortMessageProducer; 
 import org.springframework.beans.factory.annotation.Value; 
 import org.springframework.kafka.core.KafkaTemplate; 
 import org.springframework.retry.annotation.Retryable; 
 import org.springframework.retry.annotation.Backoff; 
 import org.springframework.retry.annotation.Recover; 
 import org.springframework.stereotype.Service; 
 import org.slf4j.Logger; 
 import org.slf4j.LoggerFactory; 
 import com.fasterxml.jackson.databind.ObjectMapper; 
 import com.fasterxml.jackson.core.JsonProcessingException; 
  
 @Service 
 public class InfrastructureKafkaMessageProducerImpl implements DomainPortMessageProducer { 
  
     private static final Logger logger = LoggerFactory.getLogger(InfrastructureKafkaMessageProducerImpl.class); 
  
     private final KafkaTemplate<String, String> kafkaTemplate; 
     private final ObjectMapper objectMapper; 
  
     @Value("${kafka.topic.message-processing:message-processing}") 
     private String topic; 
  
     @Value("${retry.maxAttempts:3}") 
     private int maxAttempts; 
  
     @Value("${retry.backoff.delay:2000}") 
     private long retryDelay; 
  
     @Value("${retry.backoff.multiplier:2}") 
     private double retryMultiplier; 
  
     public InfrastructureKafkaMessageProducerImpl(KafkaTemplate<String, String> kafkaTemplate) { 
         this.kafkaTemplate = kafkaTemplate; 
         this.objectMapper = new ObjectMapper(); 
     } 
  
     @Override 
     @Retryable( 
         value = {Exception.class}, 
         maxAttemptsExpression = "${retry.maxAttempts}", 
         backoff = @Backoff( 
             delayExpression = "${retry.backoff.delay}", 
             multiplierExpression = "${retry.backoff.multiplier}" 
         ) 
     ) 
     public void sendMessage(DomainEntityMessageProcessingEvent event) { 
         String key = event.getScheduleId().toString(); 
         try { 
             String message = objectMapper.writeValueAsString(event); 
  
             kafkaTemplate.send(topic, key, message); 
             logger.info("Message sent to Kafka topic {} with key {}", topic, key); 
         } catch (JsonProcessingException e) { 
             logger.error("Failed to serialize event for Kafka message production", e); 
             throw new IllegalArgumentException("Failed to serialize event", e); 
         } catch (Exception e) { 
             logger.error("Error sending message to Kafka topic {} with key {}", topic, key, e); 
             throw e; 
         } 
     } 
  
     @Recover 
     public void recover(Exception e, DomainEntityMessageProcessingEvent event) { 
         logger.error("Retries exhausted for message with key {}. Failed to send message to Kafka.", event.getScheduleId(), e); 
         // Implement additional error handling logic here, such as sending alerts 
     } 
 } 
 