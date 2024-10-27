package ai.shreds.application.services; 
  
 import ai.shreds.application.ports.ApplicationInputPortHandleAcknowledgment; 
 import ai.shreds.application.mappers.ApplicationProcessedMessageMapper; 
 import ai.shreds.domain.services.DomainServiceAcknowledgment; 
 import ai.shreds.shared.SharedDeliveryAcknowledgmentDTO; 
 import ai.shreds.shared.SharedErrorNotificationDTO; 
 import ai.shreds.domain.value_objects.DomainValueDeliveryAcknowledgment; 
 import ai.shreds.domain.value_objects.DomainValueErrorNotification; 
 import org.springframework.stereotype.Service; 
 import lombok.RequiredArgsConstructor; 
  
 @Service 
 @RequiredArgsConstructor 
 public class ApplicationServiceHandleAcknowledgment implements ApplicationInputPortHandleAcknowledgment { 
  
     private final ApplicationProcessedMessageMapper mapper; 
     private final DomainServiceAcknowledgment domainServiceAcknowledgment; 
  
     @Override 
     public void processAcknowledgment(SharedDeliveryAcknowledgmentDTO acknowledgmentDTO) { 
         try { 
             DomainValueDeliveryAcknowledgment domainAcknowledgment = mapper.toDomain(acknowledgmentDTO); 
             domainServiceAcknowledgment.processAcknowledgment(domainAcknowledgment); 
         } catch (Exception e) { 
             // Handle exception appropriately 
             // For example, log the error and rethrow or throw a custom exception 
         } 
     } 
  
     @Override 
     public void processErrorNotification(SharedErrorNotificationDTO errorNotificationDTO) { 
         try { 
             DomainValueErrorNotification domainErrorNotification = mapper.toDomain(errorNotificationDTO); 
             domainServiceAcknowledgment.processErrorNotification(domainErrorNotification); 
         } catch (Exception e) { 
             // Handle exception appropriately 
             // For example, log the error and rethrow or throw a custom exception 
         } 
     } 
  
 }