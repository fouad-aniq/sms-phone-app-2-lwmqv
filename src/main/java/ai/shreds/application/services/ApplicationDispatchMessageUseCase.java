package ai.shreds.application.services; 
  
 import ai.shreds.application.ports.ApplicationDispatchMessageInputPort; 
 import ai.shreds.domain.entities.DomainEntityProcessedMessage; 
 import ai.shreds.domain.services.DomainServiceMessageDispatch; 
 import ai.shreds.shared.SharedProcessedMessageDTO; 
 import ai.shreds.shared.SharedProcessedMessageMapper; 
 import lombok.RequiredArgsConstructor; 
 import lombok.extern.slf4j.Slf4j; 
 import org.springframework.stereotype.Service; 
  
 @Service 
 @RequiredArgsConstructor 
 @Slf4j 
 public class ApplicationDispatchMessageUseCase implements ApplicationDispatchMessageInputPort { 
  
     private final SharedProcessedMessageMapper processedMessageMapper; 
     private final DomainServiceMessageDispatch domainServiceMessageDispatch; 
  
     @Override 
     public void dispatchMessage(SharedProcessedMessageDTO message) { 
         if (!message.getValidationStatus() || !message.getPreparedForDispatch()) { 
             log.warn("Message {} cannot be dispatched: Validation failed or not prepared for dispatch.", message.getId()); 
             return; 
         } 
          
         try { 
             // Map DTO to Domain Entity 
             DomainEntityProcessedMessage domainMessage = processedMessageMapper.toDomain(message); 
              
             // Dispatch the message 
             domainServiceMessageDispatch.dispatchMessage(domainMessage); 
         } catch (Exception e) { 
             log.error("Error dispatching message {}: {}", message.getId(), e.getMessage(), e); 
         } 
     } 
 } 
 