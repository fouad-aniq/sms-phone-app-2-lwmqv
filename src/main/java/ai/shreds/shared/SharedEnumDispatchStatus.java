package ai.shreds.shared; 
  
 /** 
  * Enum representing the various dispatch statuses of a message in the dispatch process. 
  * Used throughout the application to consistently track and update message statuses. 
  */ 
 public enum SharedEnumDispatchStatus { 
     /** 
      * The message is pending dispatch and has not yet been sent to the SMS Gateway. 
      */ 
     PENDING, 
      
     /** 
      * The message has been sent to the SMS Gateway but delivery confirmation has not yet been received. 
      */ 
     SENT, 
      
     /** 
      * The message has been successfully delivered to the recipient. 
      */ 
     DELIVERED, 
      
     /** 
      * The message dispatch has failed after maximum retry attempts or due to a critical error. 
      */ 
     FAILED, 
      
     /** 
      * The message dispatch has failed but is being retried according to the retry policy. 
      */ 
     RETRYING 
 } 
 