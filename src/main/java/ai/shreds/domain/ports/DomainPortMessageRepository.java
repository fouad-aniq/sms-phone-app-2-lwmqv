package ai.shreds.domain.ports; 
  
 import ai.shreds.domain.entities.DomainEntityProcessedMessage; 
 import ai.shreds.shared.SharedEnumDispatchStatus; 
 import java.util.List; 
  
 public interface DomainPortMessageRepository { 
      
     /** 
      * Saves or updates a ProcessedMessage entity in the database. 
      *  
      * @param message the processed message entity to save or update 
      */ 
     void save(DomainEntityProcessedMessage message); 
      
     /** 
      * Retrieves a ProcessedMessage entity by its unique identifier. 
      *  
      * @param messageId the unique identifier of the processed message 
      * @return the processed message entity, or null if not found 
      */ 
     DomainEntityProcessedMessage findById(String messageId); 
      
     /** 
      * Finds all ProcessedMessage entities that have a specific dispatch status. 
      *  
      * @param status the dispatch status to search for 
      * @return a list of processed message entities with the specified dispatch status 
      */ 
     List<DomainEntityProcessedMessage> findByDispatchStatus(SharedEnumDispatchStatus status); 
      
     /** 
      * Deletes a ProcessedMessage entity from the database using its unique identifier. 
      *  
      * @param messageId the unique identifier of the processed message to delete 
      */ 
     void deleteById(String messageId); 
      
     /** 
      * Counts the number of messages with a specific dispatch status. 
      *  
      * @param status the dispatch status to count 
      * @return the number of processed messages with the specified dispatch status 
      */ 
     Integer countByDispatchStatus(SharedEnumDispatchStatus status); 
 } 
 