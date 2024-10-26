package ai.shreds.application.ports; 
  
 import java.util.UUID; 
  
 import ai.shreds.domain.entities.DomainScheduleEntity; 
  
 /** 
  * ApplicationCacheSyncPort defines methods for synchronizing the schedule data 
  * between the application layer and the caching layer. 
  */ 
 public interface ApplicationCacheSyncPort { 
  
     /** 
      * Synchronizes the given schedule to the cache. 
      *  
      * @param schedule the DomainScheduleEntity to be synchronized to the cache 
      */ 
     void syncScheduleToCache(DomainScheduleEntity schedule); 
  
     /** 
      * Removes the schedule with the specified ID from the cache. 
      *  
      * @param scheduleId the UUID of the schedule to be removed from the cache 
      */ 
     void removeScheduleFromCache(UUID scheduleId); 
 } 
 