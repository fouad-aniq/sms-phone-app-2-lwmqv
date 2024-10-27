package ai.shreds.application.services; 
  
 import ai.shreds.application.ports.ApplicationInputPortMessageProcessing; 
 import ai.shreds.shared.SharedMessageRequestDTO; 
 import ai.shreds.shared.SharedProcessedMessageDTO; 
 import ai.shreds.domain.entities.DomainEntityMessageRequest; 
 import ai.shreds.domain.entities.DomainEntityProcessedMessage; 
 import ai.shreds.domain.services.DomainServiceMessageProcessing; 
 import ai.shreds.application.mappers.MessageMapper; 
 import org.springframework.stereotype.Service; 
  
 @Service 
 public class ApplicationServiceMessageProcessingCoordinator implements ApplicationInputPortMessageProcessing { 
  
     private final DomainServiceMessageProcessing messageProcessingService; 
     private final MessageMapper messageMapper; 
  
     public ApplicationServiceMessageProcessingCoordinator(DomainServiceMessageProcessing messageProcessingService, 
                                                           MessageMapper messageMapper) { 
         this.messageProcessingService = messageProcessingService; 
         this.messageMapper = messageMapper; 
     } 
  
     @Override 
     public SharedProcessedMessageDTO processMessage(SharedMessageRequestDTO message) { 
         // Map DTO to Domain Entity 
         DomainEntityMessageRequest domainMessageRequest = messageMapper.toDomain(message); 
  
         // Process the message 
         DomainEntityProcessedMessage domainProcessedMessage = messageProcessingService.processMessage(domainMessageRequest); 
  
         // Map Domain Entity to DTO 
         SharedProcessedMessageDTO processedMessageDTO = messageMapper.toDTO(domainProcessedMessage); 
  
         // Return the DTO 
         return processedMessageDTO; 
     } 
 } 
 