package ai.shreds.application.services;

import ai.shreds.application.ports.ApplicationInputPortMessageDispatchUseCase;
import ai.shreds.application.ports.ApplicationOutputPortMessageRepositoryPort;
import ai.shreds.domain.entities.DomainEntityProcessedMessage;
import ai.shreds.shared.SharedEnumDeliveryStatus;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationServiceMessageProcessingScheduler {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationServiceMessageProcessingScheduler.class);

    private final ApplicationInputPortMessageDispatchUseCase applicationServiceMessageDispatchService;

    private final ApplicationOutputPortMessageRepositoryPort messageRepositoryPort;

    public void scheduleDispatchTasks() {
        logger.info("Message dispatch tasks scheduling initialized.");
    }

    @Scheduled(fixedDelay = 300000) // runs every 5 minutes
    public void executeDispatch() {
        logger.info("Executing message dispatch task.");

        List<DomainEntityProcessedMessage> messagesToDispatch = messageRepositoryPort.findByDispatchStatus(SharedEnumDeliveryStatus.PENDING.name());

        for (DomainEntityProcessedMessage processedMessage : messagesToDispatch) {
            if (Boolean.TRUE.equals(processedMessage.getPreparedForDispatch())) {
                try {
                    applicationServiceMessageDispatchService.dispatchMessage(processedMessage);
                } catch (Exception e) {
                    logger.error("Error dispatching message with ID: {}", processedMessage.getId(), e);
                    applicationServiceMessageDispatchService.updateMessageStatus(processedMessage.getId(), SharedEnumDeliveryStatus.FAILED.name());
                }
            } else {
                logger.info("Message with ID: {} is not prepared for dispatch.", processedMessage.getId());
            }
        }
    }
}