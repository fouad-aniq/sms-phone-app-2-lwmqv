package ai.shreds.domain.services; 
  
 import ai.shreds.domain.entities.DomainEntityDeliveryDetails; 
 import ai.shreds.domain.entities.DomainEntityProcessedMessage; 
 import ai.shreds.domain.ports.DomainPortDeliveryDetailsRepository; 
 import ai.shreds.domain.ports.DomainPortMessageRepository; 
 import ai.shreds.shared.SharedAcknowledgmentDTO; 
 import ai.shreds.shared.SharedErrorNotificationDTO; 
 import ai.shreds.shared.SharedEnumDispatchStatus; 
 import org.slf4j.Logger; 
 import org.slf4j.LoggerFactory; 
  
 import java.util.Date; 
  
 public class DomainServiceAcknowledgment { 
  
     private static final Logger logger = LoggerFactory.getLogger(DomainServiceAcknowledgment.class); 
  
     private final DomainPortMessageRepository messageRepository; 
     private final DomainPortDeliveryDetailsRepository deliveryDetailsRepository; 
  
     public DomainServiceAcknowledgment(DomainPortMessageRepository messageRepository, 
                                        DomainPortDeliveryDetailsRepository deliveryDetailsRepository) { 
         this.messageRepository = messageRepository; 
         this.deliveryDetailsRepository = deliveryDetailsRepository; 
     } 
  
     public void processAcknowledgment(SharedAcknowledgmentDTO acknowledgment) { 
         // Retrieve the ProcessedMessage by messageId 
         DomainEntityProcessedMessage processedMessage = messageRepository.findById(acknowledgment.getMessageId()); 
         if (processedMessage != null) { 
             // Update dispatchStatus to DELIVERED 
             processedMessage.setDispatchStatus(SharedEnumDispatchStatus.DELIVERED); 
             // Update dispatchTimestamp 
             processedMessage.setDispatchTimestamp(acknowledgment.getDeliveryTimestamp()); 
             // Save updated ProcessedMessage 
             messageRepository.save(processedMessage); 
  
             // Create DeliveryDetails entity 
             DomainEntityDeliveryDetails deliveryDetails = new DomainEntityDeliveryDetails(); 
             deliveryDetails.setMessageId(acknowledgment.getMessageId()); 
             deliveryDetails.setDeliveryStatus(acknowledgment.getDispatchStatus()); 
             deliveryDetails.setDeliveryTimestamp(acknowledgment.getDeliveryTimestamp()); 
             deliveryDetails.setProviderResponse(acknowledgment.getDetails()); 
             // Save DeliveryDetails 
             deliveryDetailsRepository.save(deliveryDetails); 
  
             // Notify business systems 
             notifyBusinessSystems(acknowledgment); 
         } else { 
             // Handle case where message is not found 
             logger.error("ProcessedMessage not found for messageId: {}", acknowledgment.getMessageId()); 
         } 
     } 
  
     public void processErrorNotification(SharedErrorNotificationDTO notification) { 
         // Retrieve the ProcessedMessage by messageId 
         DomainEntityProcessedMessage processedMessage = messageRepository.findById(notification.getMessageId()); 
         if (processedMessage != null) { 
             // Update dispatchStatus to FAILED 
             processedMessage.setDispatchStatus(SharedEnumDispatchStatus.FAILED); 
             // Update dispatchTimestamp to current date/time 
             processedMessage.setDispatchTimestamp(new Date()); 
             // Save updated ProcessedMessage 
             messageRepository.save(processedMessage); 
  
             // Create DeliveryDetails entity 
             DomainEntityDeliveryDetails deliveryDetails = new DomainEntityDeliveryDetails(); 
             deliveryDetails.setMessageId(notification.getMessageId()); 
             deliveryDetails.setDeliveryStatus(notification.getDispatchStatus()); 
             deliveryDetails.setDeliveryTimestamp(new Date()); 
             deliveryDetails.setErrorCode(notification.getErrorCode()); 
             deliveryDetails.setErrorMessage(notification.getErrorMessage()); 
             // Save DeliveryDetails 
             deliveryDetailsRepository.save(deliveryDetails); 
  
             // Possibly trigger retry mechanism (implementation depends on retry policy) 
         } else { 
             // Handle case where message is not found 
             logger.error("ProcessedMessage not found for messageId: {}", notification.getMessageId()); 
         } 
     } 
  
     public void notifyBusinessSystems(SharedAcknowledgmentDTO acknowledgment) { 
         // Implement notification logic 
         logger.info("Notifying business systems of message delivery: messageId={}", acknowledgment.getMessageId()); 
         // Additional implementation as required 
     } 
 } 
 