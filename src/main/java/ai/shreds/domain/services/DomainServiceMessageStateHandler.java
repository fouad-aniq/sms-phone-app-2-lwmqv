package ai.shreds.domain.services;

import ai.shreds.domain.entities.DomainEntityProcessedMessage;
import ai.shreds.shared.SharedEnumDeliveryStatus;
import ai.shreds.domain.repositories.ProcessedMessageRepository;
import ai.shreds.domain.value_objects.DomainValueDeliveryDetails;
import ai.shreds.domain.exceptions.MessageNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DomainServiceMessageStateHandler {

    private final ProcessedMessageRepository processedMessageRepository;

    public void updateDispatchStatus(String messageId, SharedEnumDeliveryStatus status) throws MessageNotFoundException {
        DomainEntityProcessedMessage message = processedMessageRepository.findById(messageId);
        if (message != null) {
            message.setDispatchStatus(status);
            message.setUpdatedAt(LocalDateTime.now());
            processedMessageRepository.save(message);
        } else {
            throw new MessageNotFoundException("ProcessedMessage with id " + messageId + " not found.");
        }
    }

    public void handleDeliveryDetails(String messageId, DomainValueDeliveryDetails details) throws MessageNotFoundException {
        DomainEntityProcessedMessage message = processedMessageRepository.findById(messageId);
        if (message != null) {
            message.setDeliveryDetails(details);
            message.setUpdatedAt(LocalDateTime.now());
            processedMessageRepository.save(message);
        } else {
            throw new MessageNotFoundException("ProcessedMessage with id " + messageId + " not found.");
        }
    }
}