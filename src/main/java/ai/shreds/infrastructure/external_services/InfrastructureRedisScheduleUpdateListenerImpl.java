package ai.shreds.infrastructure.external_services; 
  
 import ai.shreds.domain.entities.DomainEntitySchedule; 
 import ai.shreds.domain.ports.DomainPortScheduleUpdateListener; 
 import ai.shreds.shared.SharedScheduleAddedEvent; 
 import ai.shreds.shared.SharedScheduleRemovedEvent; 
 import ai.shreds.shared.SharedScheduleUpdatedEvent; 
 import ai.shreds.shared.SharedValueScheduleStatus; 
 import com.fasterxml.jackson.databind.ObjectMapper; 
 import lombok.extern.slf4j.Slf4j; 
 import org.springframework.beans.factory.annotation.Autowired; 
 import org.springframework.data.redis.connection.Message; 
 import org.springframework.data.redis.connection.MessageListener; 
 import org.springframework.data.redis.listener.PatternTopic; 
 import org.springframework.data.redis.listener.RedisMessageListenerContainer; 
 import org.springframework.stereotype.Component; 
  
 import java.util.UUID; 
  
 @Slf4j 
 @Component 
 public class InfrastructureRedisScheduleUpdateListenerImpl implements DomainPortScheduleUpdateListener, MessageListener { 
  
     private final ObjectMapper objectMapper; 
  
     @Autowired 
     public InfrastructureRedisScheduleUpdateListenerImpl(RedisMessageListenerContainer listenerContainer, ObjectMapper objectMapper) { 
         this.objectMapper = objectMapper; 
  
         // Subscribe to Redis channels 
         listenerContainer.addMessageListener(this, new PatternTopic("schedule:add")); 
         listenerContainer.addMessageListener(this, new PatternTopic("schedule:update")); 
         listenerContainer.addMessageListener(this, new PatternTopic("schedule:remove")); 
     } 
  
     @Override 
     public void onMessage(Message message, byte[] pattern) { 
         String channel = new String(message.getChannel()); 
         String messageBody = new String(message.getBody()); 
         try { 
             if ("schedule:add".equals(channel)) { 
                 SharedScheduleAddedEvent event = objectMapper.readValue(messageBody, SharedScheduleAddedEvent.class); 
                 DomainEntitySchedule schedule = convertToDomainEntitySchedule(event); 
                 onScheduleAdded(schedule); 
             } else if ("schedule:update".equals(channel)) { 
                 SharedScheduleUpdatedEvent event = objectMapper.readValue(messageBody, SharedScheduleUpdatedEvent.class); 
                 DomainEntitySchedule schedule = convertToDomainEntitySchedule(event); 
                 onScheduleUpdated(schedule); 
             } else if ("schedule:remove".equals(channel)) { 
                 SharedScheduleRemovedEvent event = objectMapper.readValue(messageBody, SharedScheduleRemovedEvent.class); 
                 UUID scheduleId = event.getScheduleId(); 
                 onScheduleRemoved(scheduleId); 
             } 
         } catch (Exception e) { 
             // Handle exception (e.g., logging) 
             log.error("Error processing message from Redis channel: {}", channel, e); 
         } 
     } 
  
     @Override 
     public void onScheduleAdded(DomainEntitySchedule schedule) { 
         // Implement the logic for handling schedule addition 
     } 
  
     @Override 
     public void onScheduleUpdated(DomainEntitySchedule schedule) { 
         // Implement the logic for handling schedule updates 
     } 
  
     @Override 
     public void onScheduleRemoved(UUID scheduleId) { 
         // Implement the logic for handling schedule removal 
     } 
  
     private DomainEntitySchedule convertToDomainEntitySchedule(SharedScheduleAddedEvent event) { 
         DomainEntitySchedule schedule = new DomainEntitySchedule(); 
         schedule.setScheduleId(event.getScheduleId()); 
         schedule.setMessageContent(event.getMessageContent()); 
         schedule.setRecipient(event.getRecipient()); 
         schedule.setScheduledTime(event.getScheduledTime()); 
         schedule.setStatus(SharedValueScheduleStatus.valueOf(event.getStatus())); 
         schedule.setCreatedAt(event.getCreatedAt()); 
         schedule.setUpdatedAt(event.getUpdatedAt()); 
         return schedule; 
     } 
  
     private DomainEntitySchedule convertToDomainEntitySchedule(SharedScheduleUpdatedEvent event) { 
         DomainEntitySchedule schedule = new DomainEntitySchedule(); 
         schedule.setScheduleId(event.getScheduleId()); 
         schedule.setMessageContent(event.getMessageContent()); 
         schedule.setRecipient(event.getRecipient()); 
         schedule.setScheduledTime(event.getScheduledTime()); 
         schedule.setStatus(SharedValueScheduleStatus.valueOf(event.getStatus())); 
         schedule.setCreatedAt(event.getCreatedAt()); 
         schedule.setUpdatedAt(event.getUpdatedAt()); 
         return schedule; 
     } 
 } 
 