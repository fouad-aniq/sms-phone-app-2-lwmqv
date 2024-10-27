package ai.shreds.infrastructure.repositories; 
  
 import ai.shreds.domain.entities.DomainEntityProcessedMessage; 
 import ai.shreds.domain.value_objects.DomainValueDispatchStatus; 
 import ai.shreds.domain.ports.DomainPortMessageRepository; 
 import ai.shreds.infrastructure.mongodb.entities.ProcessedMessageMongoEntity; 
 import ai.shreds.infrastructure.mongodb.repositories.ProcessedMessageMongoRepository; 
 import ai.shreds.infrastructure.mongodb.mappers.ProcessedMessageEntityMapper; 
 import org.springframework.beans.factory.annotation.Autowired; 
 import org.springframework.stereotype.Repository; 
  
 import java.util.List; 
 import java.util.Optional; 
 import java.util.stream.Collectors; 
  
 @Repository 
 public class InfrastructureRepositoryImplProcessedMessage implements DomainPortMessageRepository { 
  
     @Autowired 
     private ProcessedMessageMongoRepository processedMessageMongoRepository; 
  
     @Autowired 
     private ProcessedMessageEntityMapper processedMessageEntityMapper; 
  
     @Override 
     public void save(DomainEntityProcessedMessage message) { 
         // Convert domain entity to MongoDB entity 
         ProcessedMessageMongoEntity entity = processedMessageEntityMapper.toMongoEntity(message); 
         // Save entity to MongoDB 
         processedMessageMongoRepository.save(entity); 
     } 
  
     @Override 
     public DomainEntityProcessedMessage findById(String messageId) { 
         // Retrieve entity from MongoDB 
         Optional<ProcessedMessageMongoEntity> optionalEntity = processedMessageMongoRepository.findById(messageId); 
         if (optionalEntity.isPresent()) { 
             // Convert MongoDB entity to domain entity 
             return processedMessageEntityMapper.toDomainEntity(optionalEntity.get()); 
         } 
         // Handle not found case as per business requirements 
         return null; 
     } 
  
     @Override 
     public List<DomainEntityProcessedMessage> findByDispatchStatus(DomainValueDispatchStatus status) { 
         // Retrieve entities from MongoDB by dispatch status 
         List<ProcessedMessageMongoEntity> entities = processedMessageMongoRepository.findByDispatchStatus(status.name()); 
         // Convert list of MongoDB entities to list of domain entities 
         return entities.stream() 
                 .map(processedMessageEntityMapper::toDomainEntity) 
                 .collect(Collectors.toList()); 
     } 
  
     @Override 
     public void deleteById(String messageId) { 
         // Delete entity from MongoDB by ID 
         processedMessageMongoRepository.deleteById(messageId); 
     } 
  
     @Override 
     public Integer countByDispatchStatus(DomainValueDispatchStatus status) { 
         // Count entities in MongoDB by dispatch status 
         return processedMessageMongoRepository.countByDispatchStatus(status.name()); 
     } 
 } 
 