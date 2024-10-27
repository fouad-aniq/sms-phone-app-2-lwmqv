package ai.shreds.domain.value_objects; 
  
 /** 
  * Enum representing the dispatch status of a message in the message dispatch process. 
  */ 
 public enum DomainValueDispatchStatus { 
  
     /** 
      * The message is pending dispatch. 
      */ 
     PENDING, 
  
     /** 
      * The message has been sent to the SMS Gateway. 
      */ 
     SENT, 
  
     /** 
      * The message has been delivered successfully. 
      */ 
     DELIVERED, 
  
     /** 
      * The message failed to be delivered after all retries. 
      */ 
     FAILED, 
  
     /** 
      * The message is being retried after a failure. 
      */ 
     RETRYING; 
  
 }