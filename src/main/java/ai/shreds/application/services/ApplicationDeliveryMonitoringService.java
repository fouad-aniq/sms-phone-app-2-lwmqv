package ai.shreds.application.services;

import ai.shreds.domain.ports.DomainPortMessageRepository;
import ai.shreds.domain.entities.DomainEntityProcessedMessage;
import ai.shreds.shared.SharedEnumDispatchStatus;
import ai.shreds.shared.SharedProcessedMessageMapper;
import ai.shreds.shared.SharedProcessedMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ApplicationDeliveryMonitoringService {

    private final DomainPortMessageRepository messageRepository;
    private final ApplicationMessageDispatchService messageDispatchService;
    private final SharedProcessedMessageMapper messageMapper;

    private static final Logger logger = LoggerFactory.getLogger(ApplicationDeliveryMonitoringService.class);

    private static final long PENDING_THRESHOLD_MILLIS = 5 * 60 * 1000; // 5 minutes
    private static final long SENT_THRESHOLD_MILLIS = 10 * 60 * 1000; // 10 minutes

    public void monitorPendingMessages() {
        List<DomainEntityProcessedMessage> pendingMessages = messageRepository.findByDispatchStatus(SharedEnumDispatchStatus.PENDING);
        for (DomainEntityProcessedMessage message : pendingMessages) {
            Date dispatchTimestamp = message.getDispatchTimestamp();
            if (dispatchTimestamp != null && isMessageStale(dispatchTimestamp, PENDING_THRESHOLD_MILLIS)) {
                SharedProcessedMessageDTO messageDTO = messageMapper.toDTO(message);
                messageDispatchService.sendMessageToSmsGateway(messageDTO);
            }
        }
    }

    public void identifyStalledMessages() {
        List<DomainEntityProcessedMessage> sentMessages = messageRepository.findByDispatchStatus(SharedEnumDispatchStatus.SENT);
        for (DomainEntityProcessedMessage message : sentMessages) {
            Date dispatchTimestamp = message.getDispatchTimestamp();
            if (dispatchTimestamp != null && isMessageStale(dispatchTimestamp, SENT_THRESHOLD_MILLIS)) {
                message.setDispatchStatus(SharedEnumDispatchStatus.FAILED);
                messageRepository.save(message);
                logger.warn("Message {} has been marked as FAILED due to no acknowledgment.", message.getId());
            }
        }
    }

    public void generateDispatchReports() {
        long pendingMessages = messageRepository.countByDispatchStatus(SharedEnumDispatchStatus.PENDING);
        long sentMessages = messageRepository.countByDispatchStatus(SharedEnumDispatchStatus.SENT);
        long deliveredMessages = messageRepository.countByDispatchStatus(SharedEnumDispatchStatus.DELIVERED);
        long failedMessages = messageRepository.countByDispatchStatus(SharedEnumDispatchStatus.FAILED);
        long totalMessages = pendingMessages + sentMessages + deliveredMessages + failedMessages;
        double successRate = totalMessages > 0 ? ((double) deliveredMessages / totalMessages) * 100 : 0;
        logger.info("Dispatch Report:");
        logger.info("Total Messages: {}", totalMessages);
        logger.info("Pending Messages: {}", pendingMessages);
        logger.info("Sent Messages: {}", sentMessages);
        logger.info("Delivered Messages: {}", deliveredMessages);
        logger.info("Failed Messages: {}", failedMessages);
        logger.info("Success Rate: {}%", successRate);
    }

    private boolean isMessageStale(Date dispatchTimestamp, long thresholdMillis) {
        long currentTime = System.currentTimeMillis();
        return currentTime - dispatchTimestamp.getTime() > thresholdMillis;
    }
}