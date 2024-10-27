package ai.shreds.application.services; 
  
 import org.springframework.beans.factory.annotation.Autowired; 
 import org.springframework.stereotype.Service; 
  
 import ai.shreds.application.ports.ApplicationInputPortDispatchMessage; 
 import ai.shreds.application.mappers.ApplicationProcessedMessageMapper; 
 import ai.shreds.shared.SharedProcessedMessageDTO; 
 import ai.shreds.domain.entities.DomainEntityProcessedMessage; 
 import ai.shreds.domain.services.DomainServiceMessageDispatch; 
 import ai.shreds.application.exceptions.ApplicationExceptionDispatchMessage; 
  
 @Service 
 public class ApplicationServiceDispatchMessage implements ApplicationInputPortDispatchMessage { 
  
     private final ApplicationProcessedMessageMapper mapper; 
     private final DomainServiceMessageDispatch domainServiceMessageDispatch; 
  
     @Autowired 
     public ApplicationServiceDispatchMessage( 
             ApplicationProcessedMessageMapper mapper, 
             DomainServiceMessageDispatch domainServiceMessageDispatch) { 
         this.mapper = mapper; 
         this.domainServiceMessageDispatch = domainServiceMessageDispatch; 
     } 
  
     @Override 
     public void dispatchMessage(SharedProcessedMessageDTO messageDTO) { 
         try { 
             // Check if message passed validation and is prepared for dispatch 
             if (!messageDTO.getValidationStatus() || !messageDTO.getPreparedForDispatch()) { 
                 throw new ApplicationExceptionDispatchMessage("Message is not valid or not prepared for dispatch"); 
             } 
             // Map DTO to Domain entity 
             DomainEntityProcessedMessage domainMessage = mapper.toDomain(messageDTO); 
              
             // Call domain service to send message 
             domainServiceMessageDispatch.sendMessage(domainMessage); 
         } catch (Exception e) { 
             // Handle exceptions according to business rules 
             throw new ApplicationExceptionDispatchMessage("Failed to dispatch message", e); 
         } 
     } 
 } 
 