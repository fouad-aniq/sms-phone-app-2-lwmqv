package ai.shreds.domain.services; 
  
 import ai.shreds.domain.entities.DomainEntityProcessedMessage; 
 import ai.shreds.domain.entities.SharedEnumDeliveryStatus; 
 import ai.shreds.domain.repositories.ProcessedMessageRepository; 
 import ai.shreds.domain.value_objects.DomainValueDeliveryDetails; 
 import ai.shreds.domain.exceptions.MessageNotFoundException; 
  
 import java.time.LocalDateTime; 
  
 public class DomainServiceMessageStateHandler { 
  
     private final ProcessedMessageRepository processedMessageRepository; 
  
     public DomainServiceMessageStateHandler(ProcessedMessageRepository processedMessageRepository) { 
         this.processedMessageRepository = processedMessageRepository; 
     } 
  
     public void updateDispatchStatus(String messageId, SharedEnumDeliveryStatus status) { 
         DomainEntityProcessedMessage message = processedMessageRepository.findById(messageId); 
         if (message != null) { 
             message.setDispatchStatus(status); 
             message.setUpdatedAt(LocalDateTime.now()); 
             processedMessageRepository.save(message); 
         } else { 
             throw new MessageNotFoundException("ProcessedMessage with id " + messageId + " not found."); 
         } 
     } 
  
     public void handleDeliveryDetails(String messageId, DomainValueDeliveryDetails details) { 
         DomainEntityProcessedMessage message = processedMessageRepository.findById(messageId); 
         if (message != null) { 
             message.setDeliveryDetails(details); 
             message.setUpdatedAt(LocalDateTime.now()); 
             processedMessageRepository.save(message); 
         } else { 
             throw new MessageNotFoundException("ProcessedMessage with id " + messageId + " not found."); 
         } 
     } 
 }