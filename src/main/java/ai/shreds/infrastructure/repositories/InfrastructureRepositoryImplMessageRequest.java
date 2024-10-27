package ai.shreds.infrastructure.repositories; 
  
 import ai.shreds.application.ports.ApplicationOutputPortMessageRequestRepositoryPort; 
 import ai.shreds.domain.entities.DomainEntityMessageRequest; 
 import ai.shreds.infrastructure.repositories.documents.MessageRequestDocument; 
 import ai.shreds.infrastructure.repositories.mappers.MessageRequestMapper; 
 import org.springframework.stereotype.Repository; 
  
 import java.util.List; 
 import java.util.NoSuchElementException; 
 import java.util.Optional; 
 import java.util.stream.Collectors; 
  
 @Repository 
 public class InfrastructureRepositoryImplMessageRequest implements ApplicationOutputPortMessageRequestRepositoryPort { 
  
     private final MessageRequestMongoRepository messageRequestMongoRepository; 
     private final MessageRequestMapper messageRequestMapper; 
  
     public InfrastructureRepositoryImplMessageRequest(MessageRequestMongoRepository messageRequestMongoRepository, 
                                                       MessageRequestMapper messageRequestMapper) { 
         this.messageRequestMongoRepository = messageRequestMongoRepository; 
         this.messageRequestMapper = messageRequestMapper; 
     } 
  
     @Override 
     public void save(DomainEntityMessageRequest messageRequest) { 
         MessageRequestDocument document = messageRequestMapper.toDocument(messageRequest); 
         messageRequestMongoRepository.save(document); 
     } 
  
     @Override 
     public DomainEntityMessageRequest findById(String id) { 
         Optional<MessageRequestDocument> optionalDocument = messageRequestMongoRepository.findById(id); 
         if (optionalDocument.isPresent()) { 
             return messageRequestMapper.toDomainEntity(optionalDocument.get()); 
         } else { 
             throw new NoSuchElementException("MessageRequest not found with id: " + id); 
         } 
     } 
  
     @Override 
     public void updateStatus(String id, String status) { 
         Optional<MessageRequestDocument> optionalDocument = messageRequestMongoRepository.findById(id); 
         if (optionalDocument.isPresent()) { 
             MessageRequestDocument document = optionalDocument.get(); 
             document.setStatus(status); 
             messageRequestMongoRepository.save(document); 
         } else { 
             throw new NoSuchElementException("MessageRequest not found with id: " + id); 
         } 
     } 
  
     @Override 
     public List<DomainEntityMessageRequest> findByStatus(String status) { 
         List<MessageRequestDocument> documents = messageRequestMongoRepository.findByStatus(status); 
         return documents.stream() 
                 .map(messageRequestMapper::toDomainEntity) 
                 .collect(Collectors.toList()); 
     } 
 } 
 