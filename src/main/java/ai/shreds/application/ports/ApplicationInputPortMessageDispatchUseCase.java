package ai.shreds.application.ports; 
  
 import ai.shreds.domain.entities.DomainEntityProcessedMessage; 
 import ai.shreds.shared.SharedEnumDeliveryStatus; 
 import ai.shreds.shared.SharedErrorDetails; 
  
 /** 
  * This interface defines the use cases for dispatching messages, 
  * handling delivery acknowledgments, and handling error notifications. 
  * It acts as the input port in the hexagonal architecture, allowing 
  * inbound adapters to interact with the application layer. 
  */ 
 public interface ApplicationInputPortMessageDispatchUseCase { 
  
     /** 
      * Initiates the dispatch process for a processed message. 
      * 
      * @param processedMessage the processed message to be dispatched 
      */ 
     void dispatchMessage(DomainEntityProcessedMessage processedMessage); 
  
     /** 
      * Processes delivery acknowledgments received from the SMS Gateway. 
      * 
      * @param messageId      the unique identifier of the message 
      * @param deliveryStatus the delivery status of the message 
      */ 
     void handleDeliveryAcknowledgment(String messageId, SharedEnumDeliveryStatus deliveryStatus); 
  
     /** 
      * Handles error notifications received from the SMS Gateway. 
      * 
      * @param messageId    the unique identifier of the message 
      * @param errorDetails the details of the error 
      */ 
     void handleErrorNotification(String messageId, SharedErrorDetails errorDetails); 
 } 
 