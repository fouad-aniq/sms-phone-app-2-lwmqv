package ai.shreds.application.ports; 
  
 import ai.shreds.shared.SharedScheduleAddedEvent; 
 import ai.shreds.shared.SharedScheduleUpdatedEvent; 
 import ai.shreds.shared.SharedScheduleRemovedEvent; 
  
 public interface ApplicationInputPortScheduleUpdateListener { 
     void onScheduleAdded(SharedScheduleAddedEvent event); 
     void onScheduleUpdated(SharedScheduleUpdatedEvent event); 
     void onScheduleRemoved(SharedScheduleRemovedEvent event); 
 } 
 