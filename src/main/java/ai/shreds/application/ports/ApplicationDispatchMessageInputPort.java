package ai.shreds.application.ports; 
  
 import ai.shreds.shared.SharedProcessedMessageDTO; 
  
 public interface ApplicationDispatchMessageInputPort { 
  
     void dispatchMessage(SharedProcessedMessageDTO message); 
  
 }