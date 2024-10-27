package ai.shreds.adapter.primary; 
  
 import ai.shreds.shared.SharedProcessedMessageDTO; 
 import ai.shreds.application.ports.ApplicationInputPortDispatchMessage; 
 import org.springframework.kafka.annotation.KafkaListener; 
 import org.springframework.stereotype.Component; 
 import lombok.RequiredArgsConstructor; 
  
 @Component 
 @RequiredArgsConstructor 
 public class AdapterProcessedMessageListener { 
  
     private final ApplicationInputPortDispatchMessage dispatchMessagePort; 
  
     @KafkaListener(topics = "processed-messages-queue", groupId = "message-dispatch-group") 
     public void consumeMessage(SharedProcessedMessageDTO messageDTO) { 
         // Invoke the dispatchMessage method in the application layer 
         dispatchMessagePort.dispatchMessage(messageDTO); 
     } 
 } 
 