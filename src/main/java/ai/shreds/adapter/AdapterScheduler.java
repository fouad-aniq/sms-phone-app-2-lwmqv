package ai.shreds.adapter; 
  
 import ai.shreds.application.ports.ApplicationInputPortScheduleMonitoring; 
 import org.springframework.beans.factory.annotation.Autowired; 
 import org.springframework.scheduling.annotation.Scheduled; 
 import org.springframework.stereotype.Component; 
 import org.slf4j.Logger; 
 import org.slf4j.LoggerFactory; 
  
 @Component 
 public class AdapterScheduler { 
  
     private static final Logger logger = LoggerFactory.getLogger(AdapterScheduler.class); 
  
     private final ApplicationInputPortScheduleMonitoring applicationInputPortScheduleMonitoring; 
  
     @Autowired 
     public AdapterScheduler(ApplicationInputPortScheduleMonitoring applicationInputPortScheduleMonitoring) { 
         this.applicationInputPortScheduleMonitoring = applicationInputPortScheduleMonitoring; 
     } 
  
     public void startMonitoring() { 
         // Any initialization if needed; in this case, the scheduled method will handle periodic tasks 
     } 
  
     @Scheduled(fixedRateString = "${schedule.monitoring.fixedRate}", initialDelayString = "${schedule.monitoring.initialDelay}") 
     public void checkDueSchedules() { 
         try { 
             applicationInputPortScheduleMonitoring.checkDueSchedules(); 
         } catch (Exception e) { 
             logger.error("Error occurred while checking due schedules", e); 
         } 
     } 
 } 
 