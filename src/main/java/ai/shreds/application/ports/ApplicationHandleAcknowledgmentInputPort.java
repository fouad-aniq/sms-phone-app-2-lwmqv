package ai.shreds.application.ports; 
  
 import ai.shreds.shared.SharedAcknowledgmentDTO; 
 import ai.shreds.shared.SharedErrorNotificationDTO; 
  
 public interface ApplicationHandleAcknowledgmentInputPort { 
      
     void processAcknowledgment(SharedAcknowledgmentDTO acknowledgment); 
      
     void processErrorNotification(SharedErrorNotificationDTO notification); 
 } 
 