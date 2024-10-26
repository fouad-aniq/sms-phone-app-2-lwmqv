package ai.shreds.application.services; 
  
 import ai.shreds.application.ports.ApplicationInputPortScheduleMonitoring; 
 import ai.shreds.application.ports.ApplicationOutputPortMessageTrigger; 
 import ai.shreds.domain.entities.DomainEntitySchedule; 
 import ai.shreds.domain.services.DomainServiceScheduleMonitoring; 
 import ai.shreds.shared.SharedScheduleDTO; 
 import ai.shreds.application.services.ApplicationServiceErrorHandling; 
  
 import lombok.RequiredArgsConstructor; 
 import org.springframework.stereotype.Service; 
  
 import java.util.List; 
  
 @Service 
 @RequiredArgsConstructor 
 public class ApplicationServiceScheduleMonitoring implements ApplicationInputPortScheduleMonitoring { 
  
     private final DomainServiceScheduleMonitoring domainServiceScheduleMonitoring; 
     private final ApplicationOutputPortMessageTrigger applicationOutputPortMessageTrigger; 
     private final ApplicationServiceErrorHandling applicationServiceErrorHandling; 
  
     @Override 
     public void startMonitoring() { 
         // Implementation to start monitoring process, if necessary 
     } 
  
     @Override 
     public void checkDueSchedules() { 
         try { 
             List<DomainEntitySchedule> schedules = domainServiceScheduleMonitoring.checkDueSchedules(); 
             for (DomainEntitySchedule schedule : schedules) { 
                 try { 
                     SharedScheduleDTO scheduleDTO = mapToSharedScheduleDTO(schedule); 
                     applicationOutputPortMessageTrigger.triggerMessage(scheduleDTO); 
                 } catch (Exception e) { 
                     applicationServiceErrorHandling.handleError(e, schedule); 
                 } 
             } 
         } catch (Exception e) { 
             applicationServiceErrorHandling.handleError(e, null); 
         } 
     } 
  
     private SharedScheduleDTO mapToSharedScheduleDTO(DomainEntitySchedule schedule) { 
         SharedScheduleDTO scheduleDTO = new SharedScheduleDTO(); 
         scheduleDTO.setScheduleId(schedule.getScheduleId()); 
         scheduleDTO.setMessageContent(schedule.getMessageContent()); 
         scheduleDTO.setRecipient(schedule.getRecipient()); 
         scheduleDTO.setScheduledTime(schedule.getScheduledTime()); 
         scheduleDTO.setStatus(schedule.getStatus().getValue()); 
         scheduleDTO.setCreatedAt(schedule.getCreatedAt()); 
         scheduleDTO.setUpdatedAt(schedule.getUpdatedAt()); 
         return scheduleDTO; 
     } 
 } 
 