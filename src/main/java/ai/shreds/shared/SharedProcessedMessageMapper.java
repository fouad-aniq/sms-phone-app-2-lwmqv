package ai.shreds.shared; 
  
 import ai.shreds.domain.entities.DomainEntityProcessedMessage; 
 import ai.shreds.shared.SharedProcessedMessageDTO; 
 import ai.shreds.shared.SharedDeliveryDetailsMapper; 
 import org.mapstruct.Mapper; 
  
 @Mapper(componentModel = "spring", uses = {SharedDeliveryDetailsMapper.class}) 
 public interface SharedProcessedMessageMapper { 
  
     DomainEntityProcessedMessage toDomain(SharedProcessedMessageDTO messageDTO); 
  
     SharedProcessedMessageDTO toDTO(DomainEntityProcessedMessage messageEntity); 
 } 
 