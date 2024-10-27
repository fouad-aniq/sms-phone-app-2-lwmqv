package ai.shreds.application.ports; 
  
 import ai.shreds.domain.entities.DomainEntityMessageRequest; 
 import java.util.List; 
  
 public interface ApplicationOutputPortMessageRequestRepositoryPort { 
  
     /** 
      * Saves a new MessageRequest or updates an existing one in the database. 
      * 
      * @param messageRequest the MessageRequest entity to be saved or updated 
      */ 
     void save(DomainEntityMessageRequest messageRequest); 
  
     /** 
      * Retrieves a MessageRequest by its unique identifier. 
      * 
      * @param id the unique identifier of the MessageRequest 
      * @return the MessageRequest entity if found, or null if not found 
      */ 
     DomainEntityMessageRequest findById(String id); 
  
     /** 
      * Updates the status of a MessageRequest. 
      * 
      * @param id     the unique identifier of the MessageRequest 
      * @param status the new status to be set 
      */ 
     void updateStatus(String id, String status); 
  
     /** 
      * Finds all MessageRequests with a specific status. 
      * 
      * @param status the status to filter MessageRequests 
      * @return a list of MessageRequests matching the given status 
      */ 
     List<DomainEntityMessageRequest> findByStatus(String status); 
 }