package ai.shreds.application.services;

import ai.shreds.domain.services.DomainServiceMessageDispatch;
import ai.shreds.domain.entities.DomainEntityProcessedMessage;
import ai.shreds.shared.SharedProcessedMessageDTO;
import ai.shreds.shared.SharedProcessedMessageMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationMessageDispatchService {

    private final DomainServiceMessageDispatch domainServiceMessageDispatch;
    private final SharedProcessedMessageMapper sharedProcessedMessageMapper;

    public void sendMessageToSmsGateway(SharedProcessedMessageDTO message) {
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }
        DomainEntityProcessedMessage processedMessage = sharedProcessedMessageMapper.toDomain(message);
        domainServiceMessageDispatch.dispatchMessage(processedMessage);
    }

    public void executeRetryMechanism(SharedProcessedMessageDTO message) {
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }
        DomainEntityProcessedMessage processedMessage = sharedProcessedMessageMapper.toDomain(message);
        domainServiceMessageDispatch.executeRetryMechanism(processedMessage);
    }

}