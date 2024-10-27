package ai.shreds.application.services; 
  
 import ai.shreds.application.ports.ApplicationInputPortMessageDispatchUseCase; 
 import ai.shreds.application.ports.ApplicationOutputPortSMSGatewayClientPort; 
 import ai.shreds.application.ports.ApplicationOutputPortMessageRepositoryPort; 
 import ai.shreds.domain.entities.DomainEntityProcessedMessage; 
 import ai.shreds.domain.value_objects.DomainValueDeliveryDetails; 
 import ai.shreds.shared.SharedEnumDeliveryStatus; 
 import ai.shreds.shared.SharedErrorDetails; 
 import ai.shreds.shared.SharedSMSGatewayRequest; 
 import ai.shreds.shared.SharedSMSGatewayResponse; 
 import ai.shreds.shared.SharedUtilRetryPolicy; 
  
 import org.springframework.stereotype.Service; 
 import org.springframework.beans.factory.annotation.Autowired; 
 import org.springframework.retry.annotation.Retryable; 
 import org.springframework.retry.annotation.Backoff; 
 import org.springframework.retry.annotation.Recover; 
 import lombok.extern.slf4j.Slf4j; 
  
 import java.time.LocalDateTime; 
  
 @Service 
 @Slf4j 
 public class ApplicationServiceMessageDispatchService implements ApplicationInputPortMessageDispatchUseCase { 
      
     private final ApplicationOutputPortSMSGatewayClientPort smsGatewayClientPort; 
     private final ApplicationOutputPortMessageRepositoryPort messageRepositoryPort; 
     private final SharedUtilRetryPolicy retryPolicy; 
      
     @Autowired 
     public ApplicationServiceMessageDispatchService( 
             ApplicationOutputPortSMSGatewayClientPort smsGatewayClientPort, 
             ApplicationOutputPortMessageRepositoryPort messageRepositoryPort, 
             SharedUtilRetryPolicy retryPolicy) { 
         this.smsGatewayClientPort = smsGatewayClientPort; 
         this.messageRepositoryPort = messageRepositoryPort; 
         this.retryPolicy = retryPolicy; 
     } 
      
     @Override 
     @Retryable(value = { Exception.class }, 
                maxAttemptsExpression = "#{retryPolicy.maxAttempts}", 
                backoff = @Backoff(delayExpression = "#{retryPolicy.waitInterval.toMillis()}", 
                                   multiplierExpression = "#{retryPolicy.backoffMultiplier}")) 
     public void dispatchMessage(DomainEntityProcessedMessage processedMessage) { 
         try { 
             if (processedMessage.isPreparedForDispatch() && processedMessage.isValidationStatus()) { 
                 SharedSMSGatewayRequest request = new SharedSMSGatewayRequest(); 
                 request.setMessageId(processedMessage.getId()); 
                 // Handle absence of recipient 
                 String recipient = null; 
                 if (processedMessage.getDeliveryDetails() != null) { 
                     recipient = processedMessage.getDeliveryDetails().getRecipient(); 
                 } 
                 if (recipient == null) { 
                     log.error("Recipient not found for message {}", processedMessage.getId()); 
                     throw new IllegalArgumentException("Recipient not found"); 
                 } 
                 request.setRecipient(recipient); 
                 request.setContent(processedMessage.getPersonalizedContent()); 
                  
                 SharedSMSGatewayResponse response = smsGatewayClientPort.sendMessage(request); 
                  
                 if ("sent".equalsIgnoreCase(response.getStatus())) { 
                     processedMessage.setDispatchStatus(SharedEnumDeliveryStatus.SENT); 
                     processedMessage.setDispatchTimestamp(LocalDateTime.now()); 
                     log.info("Message {} sent successfully.", processedMessage.getId()); 
                 } else { 
                     SharedErrorDetails errorDetails = new SharedErrorDetails(); 
                     // Adjust code to avoid using getErrorCode() and getErrorMessage() 
                     errorDetails.setErrorCode(response.getStatus()); 
                     errorDetails.setErrorMessage(response.getDetails()); 
                     handleErrorNotification(processedMessage.getId(), errorDetails); 
                     return; 
                 } 
                  
                 messageRepositoryPort.save(processedMessage); 
             } else { 
                 log.warn("ProcessedMessage {} is not ready for dispatch.", processedMessage.getId()); 
             } 
         } catch (Exception e) { 
             log.error("Exception occurred while dispatching message {}: {}", processedMessage.getId(), e.getMessage()); 
             throw e; // Allow exception to be caught by @Retryable 
         } 
     } 
  
     @Override 
     public void handleDeliveryAcknowledgment(String messageId, SharedEnumDeliveryStatus deliveryStatus) { 
         DomainEntityProcessedMessage processedMessage = messageRepositoryPort.findById(messageId); 
         if (processedMessage != null) { 
             processedMessage.setDispatchStatus(deliveryStatus); 
             DomainValueDeliveryDetails deliveryDetails = new DomainValueDeliveryDetails(); 
             deliveryDetails.setDeliveryReceiptDetails("Delivery acknowledged"); 
             deliveryDetails.setProviderResponseCode("200"); 
             processedMessage.setDeliveryDetails(deliveryDetails); 
             processedMessage.setUpdatedAt(LocalDateTime.now()); 
             messageRepositoryPort.save(processedMessage); 
             log.info("Message {} delivery status updated to {}.", messageId, deliveryStatus); 
         } else { 
             log.warn("ProcessedMessage with ID {} not found.", messageId); 
         } 
     } 
  
     @Override 
     public void handleErrorNotification(String messageId, SharedErrorDetails errorDetails) { 
         DomainEntityProcessedMessage processedMessage = messageRepositoryPort.findById(messageId); 
         if (processedMessage != null) { 
             processedMessage.setDispatchStatus(SharedEnumDeliveryStatus.FAILED); 
             processedMessage.setDeliveryDetails(new DomainValueDeliveryDetails()); 
             processedMessage.getDeliveryDetails().setFailureReason(errorDetails.getErrorMessage()); 
             processedMessage.getDeliveryDetails().setProviderResponseCode(errorDetails.getErrorCode()); 
             processedMessage.setUpdatedAt(LocalDateTime.now()); 
             messageRepositoryPort.save(processedMessage); 
             log.info("Message {} marked as failed.", messageId); 
         } else { 
             log.warn("ProcessedMessage with ID {} not found.", messageId); 
         } 
     } 
      
     @Recover 
     public void recover(Exception e, DomainEntityProcessedMessage processedMessage) { 
         log.error("Failed to dispatch message {} after retries: {}", processedMessage.getId(), e.getMessage()); 
         processedMessage.setDispatchStatus(SharedEnumDeliveryStatus.FAILED); 
         processedMessage.setUpdatedAt(LocalDateTime.now()); 
         messageRepositoryPort.save(processedMessage); 
     } 
      
     public void updateMessageStatus(String messageId, String status) { 
         DomainEntityProcessedMessage processedMessage = messageRepositoryPort.findById(messageId); 
         if (processedMessage != null) { 
             processedMessage.setDispatchStatus(SharedEnumDeliveryStatus.valueOf(status)); 
             processedMessage.setUpdatedAt(LocalDateTime.now()); 
             messageRepositoryPort.save(processedMessage); 
             log.info("Message {} status updated to {}.", messageId, status); 
         } else { 
             log.warn("ProcessedMessage with ID {} not found.", messageId); 
         } 
     } 
 } 
 