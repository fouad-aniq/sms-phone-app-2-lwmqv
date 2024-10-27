package ai.shreds.domain.services;

import ai.shreds.domain.entities.DomainEntityProcessedMessage;
import ai.shreds.domain.entities.DomainEntityDeliveryDetails;
import ai.shreds.domain.exceptions.DomainExceptionAcknowledgmentProcessing;
import ai.shreds.domain.value_objects.DomainValueDeliveryAcknowledgment;
import ai.shreds.domain.value_objects.DomainValueErrorNotification;
import ai.shreds.domain.value_objects.DomainValueDispatchStatus;
import ai.shreds.domain.ports.DomainPortMessageRepository;

public class DomainServiceAcknowledgment {

    private final DomainPortMessageRepository messageRepository;

    public DomainServiceAcknowledgment(DomainPortMessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void processAcknowledgment(DomainValueDeliveryAcknowledgment acknowledgment) {
        DomainEntityProcessedMessage message = messageRepository.findById(acknowledgment.getMessageId());
        if (message == null) {
            throw new DomainExceptionAcknowledgmentProcessing("Processed message not found for id: " + acknowledgment.getMessageId());
        }
        message.setDispatchStatus(DomainValueDispatchStatus.DELIVERED);
        DomainEntityDeliveryDetails deliveryDetails = new DomainEntityDeliveryDetails();
        deliveryDetails.setDeliveryStatus(acknowledgment.getDeliveryStatus());
        deliveryDetails.setDeliveryTimestamp(acknowledgment.getDeliveryTimestamp());
        deliveryDetails.setProviderResponse(acknowledgment.getDetails());
        message.setDeliveryDetails(deliveryDetails);
        messageRepository.save(message);
    }

    public void processErrorNotification(DomainValueErrorNotification errorNotification) {
        DomainEntityProcessedMessage message = messageRepository.findById(errorNotification.getMessageId());
        if (message == null) {
            throw new DomainExceptionAcknowledgmentProcessing("Processed message not found for id: " + errorNotification.getMessageId());
        }
        message.setDispatchStatus(DomainValueDispatchStatus.FAILED);
        DomainEntityDeliveryDetails deliveryDetails = new DomainEntityDeliveryDetails();
        deliveryDetails.setDeliveryStatus(errorNotification.getDispatchStatus());
        deliveryDetails.setErrorCode(errorNotification.getErrorCode());
        deliveryDetails.setErrorMessage(errorNotification.getErrorMessage());
        message.setDeliveryDetails(deliveryDetails);
        messageRepository.save(message);
    }
}