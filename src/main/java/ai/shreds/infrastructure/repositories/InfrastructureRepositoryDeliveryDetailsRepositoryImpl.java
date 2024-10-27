package ai.shreds.infrastructure.repositories; 
  
 import ai.shreds.domain.entities.DomainEntityDeliveryDetails; 
 import ai.shreds.domain.ports.DomainPortDeliveryDetailsRepository; 
 import org.springframework.beans.factory.annotation.Autowired; 
 import org.springframework.stereotype.Repository; 
  
 @Repository 
 public class InfrastructureRepositoryDeliveryDetailsRepositoryImpl implements DomainPortDeliveryDetailsRepository { 
  
     private final DeliveryDetailsMongoRepository deliveryDetailsMongoRepository; 
     private final DeliveryDetailsMapper deliveryDetailsMapper; 
  
     @Autowired 
     public InfrastructureRepositoryDeliveryDetailsRepositoryImpl( 
             DeliveryDetailsMongoRepository deliveryDetailsMongoRepository, 
             DeliveryDetailsMapper deliveryDetailsMapper) { 
         this.deliveryDetailsMongoRepository = deliveryDetailsMongoRepository; 
         this.deliveryDetailsMapper = deliveryDetailsMapper; 
     } 
  
     @Override 
     public void save(DomainEntityDeliveryDetails details) { 
         DeliveryDetailsDocument document = deliveryDetailsMapper.toDocument(details); 
         deliveryDetailsMongoRepository.save(document); 
     } 
  
     @Override 
     public DomainEntityDeliveryDetails findByMessageId(String messageId) { 
         DeliveryDetailsDocument document = deliveryDetailsMongoRepository.findByMessageId(messageId); 
         if (document != null) { 
             return deliveryDetailsMapper.toDomain(document); 
         } else { 
             return null; 
         } 
     } 
 } 
 