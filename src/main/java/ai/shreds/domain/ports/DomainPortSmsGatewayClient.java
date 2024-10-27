package ai.shreds.domain.ports; 
  
 import ai.shreds.domain.entities.DomainEntityProcessedMessage; 
 import ai.shreds.domain.exceptions.DomainExceptionMessageDispatch; 
  
 /** 
  * DomainPortSmsGatewayClient defines the contract for sending processed messages 
  * to the SMS Gateway Integration Service. 
  */ 
 public interface DomainPortSmsGatewayClient { 
     /** 
      * Sends a processed message to the SMS Gateway Integration Service. 
      * 
      * @param message the processed message to be sent 
      * @throws DomainExceptionMessageDispatch if an error occurs during message dispatch 
      */ 
     void sendMessage(DomainEntityProcessedMessage message) throws DomainExceptionMessageDispatch; 
 } 
 