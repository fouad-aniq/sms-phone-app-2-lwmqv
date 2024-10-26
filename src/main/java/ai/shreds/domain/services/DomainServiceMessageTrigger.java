package ai.shreds.domain.services; 
  
 import ai.shreds.domain.entities.DomainEntitySchedule; 
 import ai.shreds.domain.entities.DomainEntityMessageProcessingEvent; 
 import ai.shreds.domain.ports.DomainPortMessageProducer; 
 import ai.shreds.domain.exceptions.DomainExceptionInvalidSchedule; 
  
 import java.util.UUID; 
 import java.sql.Timestamp; 
  
 import org.slf4j.Logger; 
 import org.slf4j.LoggerFactory; 
  
 public class DomainServiceMessageTrigger { 
     private static final Logger logger = LoggerFactory.getLogger(DomainServiceMessageTrigger.class); 
  
     private final DomainPortMessageProducer messageProducerPort; 
  
     public DomainServiceMessageTrigger(DomainPortMessageProducer messageProducerPort) { 
         this.messageProducerPort = messageProducerPort; 
     } 
  
     public void triggerMessage(DomainEntitySchedule schedule) { 
         if (schedule == null) { 
             throw new DomainExceptionInvalidSchedule("Schedule cannot be null"); 
         } 
         if (!"active".equalsIgnoreCase(schedule.getStatus().getValue())) { 
             logger.warn("Schedule with ID {} is not active and will not be processed.", schedule.getScheduleId()); 
             return; 
         } 
         try { 
             DomainEntityMessageProcessingEvent event = new DomainEntityMessageProcessingEvent(); 
             event.setEventId(UUID.randomUUID()); 
             event.setScheduleId(schedule.getScheduleId()); 
             event.setMessageContent(schedule.getMessageContent()); 
             event.setRecipient(schedule.getRecipient()); 
             event.setTriggeredAt(new Timestamp(System.currentTimeMillis())); 
  
             messageProducerPort.sendMessage(event); 
             logger.info("Message triggered for schedule ID {}.", schedule.getScheduleId()); 
         } catch (Exception e) { 
             logger.error("Error triggering message for schedule ID {}: {}", schedule.getScheduleId(), e.getMessage()); 
             // Optionally, implement retry logic or pass the exception to an error handling service 
             // For example: 
             // errorHandlingService.handleError(e, schedule); 
         } 
     } 
 }