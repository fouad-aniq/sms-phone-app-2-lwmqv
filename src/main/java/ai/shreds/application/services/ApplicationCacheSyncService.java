package ai.shreds.application.services; 
  
 import ai.shreds.application.ports.ApplicationCacheSyncPort; 
 import ai.shreds.domain.entities.DomainScheduleEntity; 
 import ai.shreds.domain.ports.DomainCacheRepositoryPort; 
 import java.util.UUID; 
  
 public class ApplicationCacheSyncService implements ApplicationCacheSyncPort { 
      
     private final DomainCacheRepositoryPort cacheRepositoryPort; 
      
     public ApplicationCacheSyncService(DomainCacheRepositoryPort cacheRepositoryPort) { 
         this.cacheRepositoryPort = cacheRepositoryPort; 
     } 
      
     @Override 
     public void syncScheduleToCache(DomainScheduleEntity schedule) { 
         cacheRepositoryPort.saveToCache(schedule); 
     } 
      
     @Override 
     public void removeScheduleFromCache(UUID scheduleId) { 
         cacheRepositoryPort.deleteFromCache(scheduleId); 
     } 
 } 
 