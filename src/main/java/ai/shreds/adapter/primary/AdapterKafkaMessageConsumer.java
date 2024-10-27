package ai.shreds.adapter.primary; 
  
 import ai.shreds.adapter.exceptions.AdapterExceptionHandler; 
 import ai.shreds.application.ports.ApplicationInputPortMessageProcessing; 
 import ai.shreds.shared.SharedMessageRequest; 
 import ai.shreds.shared.SharedMessageRequestDTO; 
 import org.springframework.beans.factory.annotation.Autowired; 
 import org.springframework.kafka.annotation.KafkaListener; 
 import org.springframework.stereotype.Component; 
 import org.slf4j.Logger; 
 import org.slf4j.LoggerFactory; 
  
 @Component 
 public class AdapterKafkaMessageConsumer { 
  
     private static final Logger logger = LoggerFactory.getLogger(AdapterKafkaMessageConsumer.class); 
  
     @Autowired 
     private ApplicationInputPortMessageProcessing applicationInputPortMessageProcessing; 
  
     @Autowired 
     private AdapterExceptionHandler adapterExceptionHandler; 
  
     @KafkaListener(topics = "message-processing-topic", groupId = "message-processing-group") 
     public void onMessageReceived(SharedMessageRequest message) { 
         try { 
             // Log receipt of the message 
             logger.info("Received message: {}", message); 
  
             // Map SharedMessageRequest to SharedMessageRequestDTO 
             SharedMessageRequestDTO messageDTO = mapToDTO(message); 
  
             // Process the message using the application input port 
             applicationInputPortMessageProcessing.processMessage(messageDTO); 
         } catch (Exception e) { 
             // Handle exceptions using AdapterExceptionHandler 
             adapterExceptionHandler.handleException(e); 
         } 
     } 
  
     private SharedMessageRequestDTO mapToDTO(SharedMessageRequest message) { 
         // Map fields from SharedMessageRequest to SharedMessageRequestDTO 
         SharedMessageRequestDTO dto = new SharedMessageRequestDTO(); 
         dto.setId(message.getId()); 
         dto.setContent(message.getContent()); 
         dto.setRecipient(message.getRecipient()); 
         dto.setTimestamp(message.getTimestamp()); 
         dto.setPriorityLevel(message.getPriorityLevel()); 
         dto.setMetadata(message.getMetadata()); 
         return dto; 
     } 
 } 
 