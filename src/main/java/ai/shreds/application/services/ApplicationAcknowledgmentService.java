package ai.shreds.application.services; 
  
 import ai.shreds.domain.services.DomainServiceAcknowledgment; 
 import ai.shreds.shared.SharedAcknowledgmentDTO; 
 import ai.shreds.shared.SharedErrorNotificationDTO; 
 import lombok.RequiredArgsConstructor; 
 import org.slf4j.Logger; 
 import org.slf4j.LoggerFactory; 
 import org.springframework.stereotype.Service; 
  
 @Service 
 @RequiredArgsConstructor 
 public class ApplicationAcknowledgmentService { 
  
     private static final Logger logger = LoggerFactory.getLogger(ApplicationAcknowledgmentService.class); 
  
     private final DomainServiceAcknowledgment domainServiceAcknowledgment; 
  
     public void updateMessageStatus(SharedAcknowledgmentDTO acknowledgment) { 
         logger.info("Processing acknowledgment for messageId: {}", acknowledgment.getMessageId()); 
         domainServiceAcknowledgment.processAcknowledgment(acknowledgment); 
     } 
  
     public void handleErrorNotification(SharedErrorNotificationDTO notification) { 
         logger.info("Processing error notification for messageId: {}", notification.getMessageId()); 
         domainServiceAcknowledgment.processErrorNotification(notification); 
     } 
 } 
 