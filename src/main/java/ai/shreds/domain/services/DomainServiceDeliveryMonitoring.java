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
            List<DomainEntityProcessedMessage> pendingMessages = messageRepository.findByDispatchStatus(DomainValueDispatchStatus.PENDING);
            long currentTime = System.currentTimeMillis();
            for (DomainEntityProcessedMessage message : pendingMessages) {
                Date dispatchTimestamp = message.getDispatchTimestamp();
                if (dispatchTimestamp != null) {
                    long dispatchTime = dispatchTimestamp.getTime();
                    long timeDifference = currentTime - dispatchTime;
                    if (timeDifference > getPendingThreshold()) {
                        message.setDispatchStatus(DomainValueDispatchStatus.RETRYING);
                        messageRepository.save(message);
                        logger.info("Message {} status updated to RETRYING due to pending timeout.", message.getId());
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error occurred while monitoring pending messages.", e);
        }
    }

    public void identifyStalledMessages() {
        try {
            checkAndHandleStalledMessages(DomainValueDispatchStatus.SENT);
            checkAndHandleStalledMessages(DomainValueDispatchStatus.RETRYING);
        } catch (Exception e) {
            logger.error("Error occurred while identifying stalled messages.", e);
        }
    }

    private void checkAndHandleStalledMessages(DomainValueDispatchStatus status) {
        List<DomainEntityProcessedMessage> messages = messageRepository.findByDispatchStatus(status);
        long currentTime = System.currentTimeMillis();
        for (DomainEntityProcessedMessage message : messages) {
            Date dispatchTimestamp = message.getDispatchTimestamp();
            if (dispatchTimestamp != null) {
                long dispatchTime = dispatchTimestamp.getTime();
                long timeDifference = currentTime - dispatchTime;
                if (timeDifference > getStalledThreshold()) {
                    message.setDispatchStatus(DomainValueDispatchStatus.FAILED);
                    messageRepository.save(message);
                    logger.info("Message {} status updated to FAILED due to stall in {} status.", message.getId(), status);
                }
            }
        }
    }

    public void generateDispatchReports() {
        try {
            int pendingCount = messageRepository.countByDispatchStatus(DomainValueDispatchStatus.PENDING);
            int sentCount = messageRepository.countByDispatchStatus(DomainValueDispatchStatus.SENT);
            int deliveredCount = messageRepository.countByDispatchStatus(DomainValueDispatchStatus.DELIVERED);
            int failedCount = messageRepository.countByDispatchStatus(DomainValueDispatchStatus.FAILED);
            int retryingCount = messageRepository.countByDispatchStatus(DomainValueDispatchStatus.RETRYING);
            String report = String.format("Dispatch Report:\n" +
                    "Pending messages: %d\n" +
                    "Sent messages: %d\n" +
                    "Delivered messages: %d\n" +
                    "Failed messages: %d\n" +
                    "Retrying messages: %d\n",
                    pendingCount, sentCount, deliveredCount, failedCount, retryingCount);
            logger.info(report);
        } catch (Exception e) {
            logger.error("Error occurred while generating dispatch reports.", e);
        }
    }

    private long getPendingThreshold() {
        // Implement the logic to return the pending threshold
        return 60000; // example: 1 minute
    }

    private long getStalledThreshold() {
        // Implement the logic to return the stalled threshold
        return 300000; // example: 5 minutes
    }
}