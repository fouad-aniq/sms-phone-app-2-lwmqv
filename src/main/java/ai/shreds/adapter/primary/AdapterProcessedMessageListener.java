// Package declaration 
 package ai.shreds.adapter.primary; 
  
 // Necessary imports 
 import ai.shreds.shared.SharedProcessedMessageDTO; 
 import ai.shreds.application.ports.ApplicationDispatchMessageInputPort; 
 import org.springframework.beans.factory.annotation.Autowired; 
 import org.springframework.stereotype.Component; 
 import org.slf4j.Logger; 
 import org.slf4j.LoggerFactory; 
 import org.springframework.kafka.annotation.KafkaListener; 
  
 @Component 
 public class AdapterProcessedMessageListener { 
  
     private static final Logger logger = LoggerFactory.getLogger(AdapterProcessedMessageListener.class); 
  
     private final ApplicationDispatchMessageInputPort dispatchMessageInputPort; 
  
     @Autowired 
     public AdapterProcessedMessageListener(ApplicationDispatchMessageInputPort dispatchMessageInputPort) { 
         this.dispatchMessageInputPort = dispatchMessageInputPort; 
     } 
  
     @KafkaListener(topics = "processed-messages-queue") 
     public void onMessage(SharedProcessedMessageDTO message) { 
         try { 
             if (message.getValidationStatus() && message.getPreparedForDispatch()) { 
                 dispatchMessageInputPort.dispatchMessage(message); 
             } else { 
                 logger.warn("Message with id {} is not ready for dispatch.", message.getId()); 
             } 
         } catch (Exception e) { 
             logger.error("Error processing message with id {}: {}", message.getId(), e.getMessage(), e); 
         } 
     } 
 }