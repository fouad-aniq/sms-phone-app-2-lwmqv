package ai.shreds.infrastructure.repositories; 
  
 import ai.shreds.domain.entities.DomainEntityMessageRequest; 
 import ai.shreds.domain.ports.DomainPortMessageRequestRepository; 
 import ai.shreds.infrastructure.exceptions.InfrastructureExceptionDataAccessException; 
 import org.springframework.beans.factory.annotation.Autowired; 
 import org.springframework.data.mongodb.core.MongoTemplate; 
 import org.springframework.data.mongodb.core.query.Criteria; 
 import org.springframework.data.mongodb.core.query.Query; 
 import org.springframework.stereotype.Repository; 
  
 @Repository 
 public class InfrastructureRepositoryImplMessageRequestMongo implements DomainPortMessageRequestRepository { 
  
     private final MongoTemplate mongoTemplate; 
     private final MessageRequestMapper messageRequestMapper; 
  
     @Autowired 
     public InfrastructureRepositoryImplMessageRequestMongo(MongoTemplate mongoTemplate, MessageRequestMapper messageRequestMapper) { 
         this.mongoTemplate = mongoTemplate; 
         this.messageRequestMapper = messageRequestMapper; 
     } 
  
     @Override 
     public void save(DomainEntityMessageRequest messageRequest) { 
         try { 
             MessageRequestDocument document = messageRequestMapper.toDocument(messageRequest); 
             mongoTemplate.save(document); 
         } catch (Exception e) { 
             throw new InfrastructureExceptionDataAccessException("Error saving MessageRequest: " + e.getMessage()); 
         } 
     } 
  
     @Override 
     public DomainEntityMessageRequest findById(String id) { 
         try { 
             Query query = new Query(Criteria.where("id").is(id)); 
             MessageRequestDocument document = mongoTemplate.findOne(query, MessageRequestDocument.class); 
             if (document == null) { 
                 return null; 
             } 
             return messageRequestMapper.toDomainEntity(document); 
         } catch (Exception e) { 
             throw new InfrastructureExceptionDataAccessException("Error finding MessageRequest by id: " + e.getMessage()); 
         } 
     } 
  
     @Override 
     public void deleteById(String id) { 
         try { 
             Query query = new Query(Criteria.where("id").is(id)); 
             mongoTemplate.remove(query, MessageRequestDocument.class); 
         } catch (Exception e) { 
             throw new InfrastructureExceptionDataAccessException("Error deleting MessageRequest by id: " + e.getMessage()); 
         } 
     } 
 } 
 