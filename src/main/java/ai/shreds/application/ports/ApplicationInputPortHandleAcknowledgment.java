package ai.shreds.application.ports; 
  
 import ai.shreds.shared.SharedDeliveryAcknowledgmentDTO; 
 import ai.shreds.shared.SharedErrorNotificationDTO; 
  
 public interface ApplicationInputPortHandleAcknowledgment { 
  
     void processAcknowledgment(SharedDeliveryAcknowledgmentDTO acknowledgmentDTO); 
  
     void processErrorNotification(SharedErrorNotificationDTO errorNotificationDTO); 
 } 
 