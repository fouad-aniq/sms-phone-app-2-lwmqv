package ai.shreds.application.ports; 
  
 import ai.shreds.shared.SharedProcessedMessageDTO; 
  
 /** 
  * Input port interface for dispatching processed messages in the application layer. 
  * Defines the contract for initiating the dispatch process of a processed message. 
  */ 
 public interface ApplicationInputPortDispatchMessage { 
  
     /** 
      * Initiates the dispatch process for a given processed message. 
      *  
      * @param messageDTO the processed message data transfer object containing all necessary message details. 
      */ 
     void dispatchMessage(SharedProcessedMessageDTO messageDTO); 
 } 
 