package ai.shreds.domain.ports; 
  
 import ai.shreds.domain.entities.DomainEntityMessageRequest; 
 import reactor.core.publisher.Flux; 
 import reactor.core.publisher.Mono; 
  
 public interface DomainPortMessageRequestRepository { 
      
     Mono<DomainEntityMessageRequest> findById(String messageRequestId); 
      
     Mono<Void> updateStatus(String messageRequestId, String status); 
      
     Flux<DomainEntityMessageRequest> findAll(); 
 } 
 