package ai.shreds.application.services; 
  
 import ai.shreds.application.ports.ApplicationHandleAcknowledgmentInputPort; 
 import ai.shreds.domain.services.DomainServiceAcknowledgment; 
 import ai.shreds.shared.SharedAcknowledgmentDTO; 
 import ai.shreds.shared.SharedErrorNotificationDTO; 
 import org.springframework.beans.factory.annotation.Autowired; 
 import org.springframework.stereotype.Service; 
 import org.slf4j.Logger; 
 import org.slf4j.LoggerFactory; 
  
 @Service 
 public class ApplicationHandleAcknowledgmentUseCase implements ApplicationHandleAcknowledgmentInputPort { 
  
     private static final Logger logger = LoggerFactory.getLogger(ApplicationHandleAcknowledgmentUseCase.class); 
  
     private final DomainServiceAcknowledgment domainServiceAcknowledgment; 
  
     @Autowired 
     public ApplicationHandleAcknowledgmentUseCase(DomainServiceAcknowledgment domainServiceAcknowledgment) { 
         this.domainServiceAcknowledgment = domainServiceAcknowledgment; 
     } 
  
     @Override 
     public void processAcknowledgment(SharedAcknowledgmentDTO acknowledgment) { 
         if (acknowledgment == null) { 
             throw new IllegalArgumentException("Acknowledgment cannot be null"); 
         } 
  
         logger.info("Processing acknowledgment for messageId: {}", acknowledgment.getMessageId()); 
  
         try { 
             domainServiceAcknowledgment.processAcknowledgment(acknowledgment); 
         } catch (Exception e) { 
             logger.error("Error processing acknowledgment for messageId: {}", acknowledgment.getMessageId(), e); 
             // Handle exception as needed 
         } 
     } 
  
     @Override 
     public void processErrorNotification(SharedErrorNotificationDTO notification) { 
         if (notification == null) { 
             throw new IllegalArgumentException("Error notification cannot be null"); 
         } 
  
         logger.info("Processing error notification for messageId: {}", notification.getMessageId()); 
  
         try { 
             domainServiceAcknowledgment.processErrorNotification(notification); 
         } catch (Exception e) { 
             logger.error("Error processing error notification for messageId: {}", notification.getMessageId(), e); 
             // Handle exception as needed 
         } 
     } 
 } 
 