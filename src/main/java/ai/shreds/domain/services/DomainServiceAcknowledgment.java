package ai.shreds.domain.services; 
  
 import ai.shreds.domain.entities.DomainEntityProcessedMessage; 
 import ai.shreds.domain.entities.DomainEntityDeliveryDetails; 
 import ai.shreds.domain.exceptions.DomainExceptionAcknowledgmentProcessing; 
 import ai.shreds.domain.value_objects.DomainValueDeliveryAcknowledgment; 
 import ai.shreds.domain.value_objects.DomainValueErrorNotification; 
 import ai.shreds.domain.value_objects.DomainValueDispatchStatus; 
 import ai.shreds.domain.ports.DomainPortMessageRepository; 
  
 public class DomainServiceAcknowledgment { 
  
     private final DomainPortMessageRepository messageRepository; 
  
     public DomainServiceAcknowledgment(DomainPortMessageRepository messageRepository) { 
         this.messageRepository = messageRepository; 
     } 
  
     public void processAcknowledgment(DomainValueDeliveryAcknowledgment acknowledgment) { 
         // Retrieve the processed message from the repository using messageId 
         DomainEntityProcessedMessage message = messageRepository.findById(acknowledgment.getMessageId()); 
         if (message == null) { 
             throw new DomainExceptionAcknowledgmentProcessing("Processed message not found for id: " + acknowledgment.getMessageId()); 
         } 
  
         // Update the dispatchStatus to DELIVERED 
         message.setDispatchStatus(DomainValueDispatchStatus.DELIVERED); 
  
         // Update the deliveryDetails with details from acknowledgment 
         DomainEntityDeliveryDetails deliveryDetails = new DomainEntityDeliveryDetails(); 
         deliveryDetails.setDeliveryStatus(acknowledgment.getDeliveryStatus()); 
         deliveryDetails.setDeliveryTimestamp(acknowledgment.getDeliveryTimestamp()); 
         deliveryDetails.setProviderResponse(acknowledgment.getDetails()); 
  
         message.setDeliveryDetails(deliveryDetails); 
  
         // Save the updated message back to the repository 
         messageRepository.save(message); 
     } 
  
     public void processErrorNotification(DomainValueErrorNotification errorNotification) { 
         // Retrieve the processed message from the repository using messageId 
         DomainEntityProcessedMessage message = messageRepository.findById(errorNotification.getMessageId()); 
         if (message == null) { 
             throw new DomainExceptionAcknowledgmentProcessing("Processed message not found for id: " + errorNotification.getMessageId()); 
         } 
  
         // Update the dispatchStatus to FAILED 
         message.setDispatchStatus(DomainValueDispatchStatus.FAILED); 
  
         // Update the deliveryDetails with error code and error message 
         DomainEntityDeliveryDetails deliveryDetails = new DomainEntityDeliveryDetails(); 
         deliveryDetails.setDeliveryStatus(errorNotification.getDispatchStatus()); 
         deliveryDetails.setErrorCode(errorNotification.getErrorCode()); 
         deliveryDetails.setErrorMessage(errorNotification.getErrorMessage()); 
  
         message.setDeliveryDetails(deliveryDetails); 
  
         // Save the updated message back to the repository 
         messageRepository.save(message); 
     } 
 } 
 