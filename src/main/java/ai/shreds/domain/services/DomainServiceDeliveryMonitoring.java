package ai.shreds.domain.services; 
  
 import ai.shreds.domain.entities.DomainEntityProcessedMessage; 
 import ai.shreds.domain.ports.DomainPortMessageRepository; 
 import ai.shreds.domain.value_objects.DomainValueDispatchStatus; 
  
 import org.slf4j.Logger; 
 import org.slf4j.LoggerFactory; 
  
 import java.util.Date; 
 import java.util.List; 
  
 public class DomainServiceDeliveryMonitoring { 
  
     private static final Logger logger = LoggerFactory.getLogger(DomainServiceDeliveryMonitoring.class); 
  
     private final DomainPortMessageRepository messageRepository; 
  
     public DomainServiceDeliveryMonitoring(DomainPortMessageRepository messageRepository) { 
         this.messageRepository = messageRepository; 
     } 
  
     public void monitorPendingMessages() { 
         try { 
             // Retrieve messages with dispatchStatus == PENDING 
             List<DomainEntityProcessedMessage> pendingMessages = 
                     messageRepository.findByDispatchStatus(DomainValueDispatchStatus.PENDING); 
  
             for (DomainEntityProcessedMessage message : pendingMessages) { 
                 // Check if message has been pending longer than threshold 
                 long currentTime = System.currentTimeMillis(); 
                 Date dispatchTimestamp = message.getDispatchTimestamp(); 
                 if (dispatchTimestamp == null) { 
                     continue; // Skip messages with no dispatch timestamp 
                 } 
                 long dispatchTime = dispatchTimestamp.getTime(); 
                 long timeDifference = currentTime - dispatchTime; 
  
                 if (timeDifference > getPendingThreshold()) { 
                     // Update message status to RETRYING 
                     message.setDispatchStatus(DomainValueDispatchStatus.RETRYING); 
                     messageRepository.save(message); 
                     logger.info("Message {} status updated to RETRYING due to pending timeout.", message.getId()); 
                     // Optionally, trigger retry logic 
                 } 
             } 
         } catch (Exception e) { 
             logger.error("Error occurred while monitoring pending messages.", e); 
             // Handle exception appropriately 
         } 
     } 
  
     public void identifyStalledMessages() { 
         try { 
             // Retrieve messages with dispatchStatus == SENT 
             List<DomainEntityProcessedMessage> sentMessages = 
                     messageRepository.findByDispatchStatus(DomainValueDispatchStatus.SENT); 
  
             for (DomainEntityProcessedMessage message : sentMessages) { 
                 // Check if message has been in SENT status longer than threshold 
                 long currentTime = System.currentTimeMillis(); 
                 Date dispatchTimestamp = message.getDispatchTimestamp(); 
                 if (dispatchTimestamp == null) { 
                     continue; // Skip messages with no dispatch timestamp 
                 } 
                 long dispatchTime = dispatchTimestamp.getTime(); 
                 long timeDifference = currentTime - dispatchTime; 
  
                 if (timeDifference > getStalledThreshold()) { 
                     // Update message status to FAILED 
                     message.setDispatchStatus(DomainValueDispatchStatus.FAILED); 
                     messageRepository.save(message); 
                     logger.info("Message {} status updated to FAILED due to stall in SENT status.", message.getId()); 
                     // Optionally, notify or handle accordingly 
                 } 
             } 
  
             // Retrieve messages with dispatchStatus == RETRYING 
             List<DomainEntityProcessedMessage> retryingMessages = 
                     messageRepository.findByDispatchStatus(DomainValueDispatchStatus.RETRYING); 
  
             for (DomainEntityProcessedMessage message : retryingMessages) { 
                 // Check if retry attempts exceeded max or time exceeded threshold 
                 long currentTime = System.currentTimeMillis(); 
                 Date dispatchTimestamp = message.getDispatchTimestamp(); 
                 if (dispatchTimestamp == null) { 
                     continue; 
                 } 
                 long dispatchTime = dispatchTimestamp.getTime(); 
                 long timeDifference = currentTime - dispatchTime; 
  
                 if (timeDifference > getStalledThreshold()) { 
                     // Update message status to FAILED 
                     message.setDispatchStatus(DomainValueDispatchStatus.FAILED); 
                     messageRepository.save(message); 
                     logger.info("Message {} status updated to FAILED due to stall in RETRYING status.", message.getId()); 
                     // Optionally, notify or handle accordingly 
                 } 
             } 
         } catch (Exception e) { 
             logger.error("Error occurred while identifying stalled messages.", e); 
             // Handle exception appropriately 
         } 
     } 
  
     public void generateDispatchReports() { 
         try { 
             int pendingCount = messageRepository.countByDispatchStatus(DomainValueDispatchStatus.PENDING); 
             int sentCount = messageRepository.countByDispatchStatus(DomainValueDispatchStatus.SENT); 
             int deliveredCount = messageRepository.countByDispatchStatus(DomainValueDispatchStatus.DELIVERED); 
             int failedCount = messageRepository.countByDispatchStatus(DomainValueDispatchStatus.FAILED); 
             int retryingCount = messageRepository.countByDispatchStatus(DomainValueDispatchStatus.RETRYING); 
  
             String report = String.format("Dispatch Report:%n" + 
                     "Pending messages: %d%n" + 
                     "Sent messages: %d%n" + 
                     "Delivered messages: %d%n" + 
                     "Failed messages: %d%n" + 
                     "Retrying messages: %d%n", 
                     pendingCount, sentCount, deliveredCount, failedCount, retryingCount); 
  
             // Output the report 
             logger.info(report); 
         } catch (Exception e) { 
             logger.error("Error occurred while generating dispatch reports.", e); 
             // Handle exception appropriately 
         } 
     } 
  
     private long getPendingThreshold() { 
         // Threshold for pending messages (e.g., 5 minutes) 
         return 5 * 60 * 1000; 
     } 
  
     private long getStalledThreshold() { 
         // Threshold for stalled messages (e.g., 10 minutes) 
         return 10 * 60 * 1000; 
     } 
 } 
 