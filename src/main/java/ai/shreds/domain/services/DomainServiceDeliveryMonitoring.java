package ai.shreds.domain.services; 
  
 import ai.shreds.domain.entities.DomainEntityProcessedMessage; 
 import ai.shreds.domain.ports.DomainPortMessageRepository; 
 import ai.shreds.shared.SharedEnumDispatchStatus; 
 import lombok.RequiredArgsConstructor; 
 import org.slf4j.Logger; 
 import org.slf4j.LoggerFactory; 
  
 import java.time.Duration; 
 import java.time.Instant; 
 import java.util.List; 
  
 @RequiredArgsConstructor 
 public class DomainServiceDeliveryMonitoring { 
  
     private final DomainPortMessageRepository messageRepository; 
  
     private static final Logger logger = LoggerFactory.getLogger(DomainServiceDeliveryMonitoring.class); 
  
     private static final Duration PENDING_THRESHOLD = Duration.ofMinutes(5); 
     private static final Duration SENT_THRESHOLD = Duration.ofMinutes(10); 
  
     public void monitorDispatchQueue() { 
         try { 
             List<DomainEntityProcessedMessage> pendingMessages = messageRepository.findByDispatchStatus(SharedEnumDispatchStatus.PENDING); 
             Instant now = Instant.now(); 
             for (DomainEntityProcessedMessage message : pendingMessages) { 
                 if (message.getDispatchTimestamp() != null) { 
                     Duration timeElapsed = Duration.between(message.getDispatchTimestamp().toInstant(), now); 
                     if (timeElapsed.compareTo(PENDING_THRESHOLD) > 0) { 
                         handlePendingMessageTimeout(message); 
                     } 
                 } 
             } 
         } catch (Exception e) { 
             logger.error("Error occurred while monitoring dispatch queue", e); 
         } 
     } 
  
     private void handlePendingMessageTimeout(DomainEntityProcessedMessage message) { 
         try { 
             // Update the message status to 'FAILED' 
             message.setDispatchStatus(SharedEnumDispatchStatus.FAILED); 
             // Save the updated message 
             messageRepository.save(message); 
             // Log the event or notify the system 
             logger.warn("Message {} has been pending for too long and marked as FAILED.", message.getId()); 
         } catch (Exception e) { 
             logger.error("Error occurred while handling pending message timeout for message {}", message.getId(), e); 
         } 
     } 
  
     public void detectStuckMessages() { 
         try { 
             List<DomainEntityProcessedMessage> sentMessages = messageRepository.findByDispatchStatus(SharedEnumDispatchStatus.SENT); 
             Instant now = Instant.now(); 
             for (DomainEntityProcessedMessage message : sentMessages) { 
                 if (message.getDispatchTimestamp() != null) { 
                     Duration timeElapsed = Duration.between(message.getDispatchTimestamp().toInstant(), now); 
                     if (timeElapsed.compareTo(SENT_THRESHOLD) > 0) { 
                         handleStuckMessage(message); 
                     } 
                 } 
             } 
         } catch (Exception e) { 
             logger.error("Error occurred while detecting stuck messages", e); 
         } 
     } 
  
     private void handleStuckMessage(DomainEntityProcessedMessage message) { 
         try { 
             // Update the message status to 'FAILED' 
             message.setDispatchStatus(SharedEnumDispatchStatus.FAILED); 
             // Save the updated message 
             messageRepository.save(message); 
             // Log the event or notify the system 
             logger.warn("Message {} has been stuck in SENT status and marked as FAILED.", message.getId()); 
         } catch (Exception e) { 
             logger.error("Error occurred while handling stuck message {}", message.getId(), e); 
         } 
     } 
  
     public void generateDispatchReports() { 
         try { 
             long pendingMessages = messageRepository.countByDispatchStatus(SharedEnumDispatchStatus.PENDING); 
             long sentMessages = messageRepository.countByDispatchStatus(SharedEnumDispatchStatus.SENT); 
             long deliveredMessages = messageRepository.countByDispatchStatus(SharedEnumDispatchStatus.DELIVERED); 
             long failedMessages = messageRepository.countByDispatchStatus(SharedEnumDispatchStatus.FAILED); 
             long retryingMessages = messageRepository.countByDispatchStatus(SharedEnumDispatchStatus.RETRYING); 
  
             // Calculate totalMessages by summing counts 
             long totalMessages = pendingMessages + sentMessages + deliveredMessages + failedMessages + retryingMessages; 
  
             double successRate = totalMessages > 0 ? ((double) deliveredMessages / totalMessages) * 100 : 0; 
             double failureRate = totalMessages > 0 ? ((double) failedMessages / totalMessages) * 100 : 0; 
  
             // Generate report 
             logger.info("Dispatch Report:"); 
             logger.info("Total Messages: {}", totalMessages); 
             logger.info("Pending Messages: {}", pendingMessages); 
             logger.info("Sent Messages: {}", sentMessages); 
             logger.info("Delivered Messages: {}", deliveredMessages); 
             logger.info("Failed Messages: {}", failedMessages); 
             logger.info("Retrying Messages: {}", retryingMessages); 
             logger.info("Success Rate: {}%", successRate); 
             logger.info("Failure Rate: {}%", failureRate); 
         } catch (Exception e) { 
             logger.error("Error occurred while generating dispatch reports", e); 
         } 
     } 
 } 
 