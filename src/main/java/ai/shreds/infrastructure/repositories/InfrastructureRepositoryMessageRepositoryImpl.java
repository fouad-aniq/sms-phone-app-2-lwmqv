package ai.shreds.infrastructure.repositories; 
  
 import ai.shreds.domain.entities.DomainEntityProcessedMessage; 
 import ai.shreds.domain.ports.DomainPortMessageRepository; 
 import ai.shreds.shared.SharedEnumDispatchStatus; 
  
 import org.springframework.beans.factory.annotation.Autowired; 
 import org.springframework.data.mongodb.core.MongoTemplate; 
 import org.springframework.data.mongodb.core.query.Criteria; 
 import org.springframework.data.mongodb.core.query.Query; 
 import org.springframework.stereotype.Repository; 
  
 import java.util.List; 
  
 @Repository 
 public class InfrastructureRepositoryMessageRepositoryImpl implements DomainPortMessageRepository { 
  
     @Autowired 
     private MongoTemplate mongoTemplate; 
  
     @Override 
     public void save(DomainEntityProcessedMessage message) { 
         mongoTemplate.save(message); 
     } 
  
     @Override 
     public DomainEntityProcessedMessage findById(String messageId) { 
         return mongoTemplate.findById(messageId, DomainEntityProcessedMessage.class); 
     } 
  
     @Override 
     public List<DomainEntityProcessedMessage> findByDispatchStatus(SharedEnumDispatchStatus status) { 
         Query query = new Query(); 
         query.addCriteria(Criteria.where("dispatchStatus").is(status.toString())); 
         return mongoTemplate.find(query, DomainEntityProcessedMessage.class); 
     } 
  
     @Override 
     public void deleteById(String messageId) { 
         Query query = new Query(); 
         query.addCriteria(Criteria.where("id").is(messageId)); 
         mongoTemplate.remove(query, DomainEntityProcessedMessage.class); 
     } 
  
     @Override 
     public Integer countByDispatchStatus(SharedEnumDispatchStatus status) { 
         Query query = new Query(); 
         query.addCriteria(Criteria.where("dispatchStatus").is(status.toString())); 
         long count = mongoTemplate.count(query, DomainEntityProcessedMessage.class); 
         return (int) count; 
     } 
 } 
 