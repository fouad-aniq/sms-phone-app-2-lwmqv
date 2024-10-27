package ai.shreds.infrastructure.repositories; 
  
 import ai.shreds.domain.entities.DomainEntityDeliveryDetails; 
 import ai.shreds.domain.ports.DomainPortDeliveryDetailsRepository; 
 import ai.shreds.infrastructure.entities.DeliveryDetailsEntity; 
 import ai.shreds.application.mappers.DeliveryDetailsMapper; 
  
 import org.springframework.beans.factory.annotation.Autowired; 
 import org.springframework.stereotype.Repository; 
  
 @Repository 
 public class InfrastructureRepositoryImplDeliveryDetails implements DomainPortDeliveryDetailsRepository { 
  
     @Autowired 
     private DeliveryDetailsMongoRepository deliveryDetailsMongoRepository; 
  
     @Autowired 
     private DeliveryDetailsMapper deliveryDetailsMapper; 
  
     @Override 
     public void save(DomainEntityDeliveryDetails deliveryDetails) { 
         // Map DomainEntityDeliveryDetails to DeliveryDetailsEntity 
         DeliveryDetailsEntity entity = deliveryDetailsMapper.toEntity(deliveryDetails); 
         // Save the DeliveryDetailsEntity to MongoDB 
         deliveryDetailsMongoRepository.save(entity); 
     } 
  
     @Override 
     public DomainEntityDeliveryDetails findByMessageId(String messageId) { 
         // Find the DeliveryDetailsEntity by messageId 
         DeliveryDetailsEntity entity = deliveryDetailsMongoRepository.findByMessageId(messageId); 
         if (entity != null) { 
             // Map DeliveryDetailsEntity to DomainEntityDeliveryDetails 
             return deliveryDetailsMapper.toDomain(entity); 
         } else { 
             return null; 
         } 
     } 
 } 
 