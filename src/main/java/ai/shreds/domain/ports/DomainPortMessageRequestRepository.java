package ai.shreds.domain.ports; 
  
 import ai.shreds.domain.entities.DomainEntityMessageRequest; 
  
 public interface DomainPortMessageRequestRepository { 
     void save(DomainEntityMessageRequest messageRequest); 
     DomainEntityMessageRequest findById(String id); 
     void deleteById(String id); 
 }