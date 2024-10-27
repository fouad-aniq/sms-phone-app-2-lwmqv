package ai.shreds.domain.services;

import ai.shreds.domain.entities.DomainEntityDeliveryDetails;
import ai.shreds.domain.entities.DomainEntityProcessedMessage;
import ai.shreds.domain.ports.DomainPortDeliveryDetailsRepository;
import ai.shreds.domain.ports.DomainPortMessageRepository;
import ai.shreds.shared.SharedAcknowledgmentDTO;
import ai.shreds.shared.SharedErrorNotificationDTO;
import ai.shreds.shared.SharedEnumDispatchStatus;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

@RequiredArgsConstructor
public class DomainServiceAcknowledgment {

    private static final Logger logger = LoggerFactory.getLogger(DomainServiceAcknowledgment.class);

    private final DomainPortMessageRepository messageRepository;
    private final DomainPortDeliveryDetailsRepository deliveryDetailsRepository;

    public void processAcknowledgment(SharedAcknowledgmentDTO acknowledgment) {
        DomainEntityProcessedMessage processedMessage = messageRepository.findById(acknowledgment.getMessageId());
        if (processedMessage != null) {
            processedMessage.setDispatchStatus(SharedEnumDispatchStatus.DELIVERED);
            processedMessage.setDispatchTimestamp(acknowledgment.getDeliveryTimestamp());
            messageRepository.save(processedMessage);

            DomainEntityDeliveryDetails deliveryDetails = new DomainEntityDeliveryDetails();
            deliveryDetails.setMessageId(acknowledgment.getMessageId());
            deliveryDetails.setDeliveryStatus(acknowledgment.getDispatchStatus());
            deliveryDetails.setDeliveryTimestamp(acknowledgment.getDeliveryTimestamp());
            deliveryDetails.setProviderResponse(acknowledgment.getDetails());
            deliveryDetailsRepository.save(deliveryDetails);

            notifyBusinessSystems(acknowledgment);
        } else {
            logger.error("ProcessedMessage not found for messageId: {}", acknowledgment.getMessageId());
        }
    }

    public void processErrorNotification(SharedErrorNotificationDTO notification) {
        DomainEntityProcessedMessage processedMessage = messageRepository.findById(notification.getMessageId());
        if (processedMessage != null) {
            processedMessage.setDispatchStatus(SharedEnumDispatchStatus.FAILED);
            processedMessage.setDispatchTimestamp(new Date());
            messageRepository.save(processedMessage);

            DomainEntityDeliveryDetails deliveryDetails = new DomainEntityDeliveryDetails();
            deliveryDetails.setMessageId(notification.getMessageId());
            deliveryDetails.setDeliveryStatus(notification.getDispatchStatus());
            deliveryDetails.setDeliveryTimestamp(new Date());
            deliveryDetails.setErrorCode(notification.getErrorCode());
            deliveryDetails.setErrorMessage(notification.getErrorMessage());
            deliveryDetailsRepository.save(deliveryDetails);
        } else {
            logger.error("ProcessedMessage not found for messageId: {}", notification.getMessageId());
        }
    }

    public void notifyBusinessSystems(SharedAcknowledgmentDTO acknowledgment) {
        logger.info("Notifying business systems of message delivery: messageId={}", acknowledgment.getMessageId());
    }
}