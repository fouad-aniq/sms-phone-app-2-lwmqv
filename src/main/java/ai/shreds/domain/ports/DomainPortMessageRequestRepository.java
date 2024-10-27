package ai.shreds.domain.ports; 
  
 import ai.shreds.domain.entities.DomainEntityMessageRequest; 
 import java.util.List; 
  
 public interface DomainPortMessageRequestRepository { 
  
     /** 
      * Retrieves a MessageRequest entity by its unique identifier. 
      * 
      * @param id the unique identifier of the MessageRequest 
      * @return the DomainEntityMessageRequest associated with the given id 
      */ 
     DomainEntityMessageRequest findById(String id); 
  
     /** 
      * Updates the status of a MessageRequest entity. 
      * 
      * @param id the unique identifier of the MessageRequest 
      * @param status the new status to set 
      */ 
     void updateStatus(String id, String status); 
  
     /** 
      * Retrieves all MessageRequest entities. 
      * 
      * @return a list of all DomainEntityMessageRequest entities 
      */ 
     List<DomainEntityMessageRequest> findAll(); 
  
 }