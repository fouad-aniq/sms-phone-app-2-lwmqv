package ai.shreds.domain.ports; 
  
 import ai.shreds.domain.entities.DomainEntityDeliveryDetails; 
  
 public interface DomainPortDeliveryDetailsRepository { 
  
     void save(DomainEntityDeliveryDetails deliveryDetails); 
  
     DomainEntityDeliveryDetails findByMessageId(String messageId); 
  
 } 
 