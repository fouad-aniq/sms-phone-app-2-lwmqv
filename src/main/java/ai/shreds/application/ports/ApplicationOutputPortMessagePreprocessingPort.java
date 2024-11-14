package ai.shreds.application.ports;

import ai.shreds.domain.entities.DomainEntitySMSMessage;
import ai.shreds.domain.entities.SharedEnumMessageStatusEnum;
import ai.shreds.domain.services.DomainServiceMessagePreprocessingComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of ApplicationOutputPortMessagePreprocessingPort.
 */
@Service
public class ApplicationOutputPortMessagePreprocessingPortImpl implements ApplicationOutputPortMessagePreprocessingPort {

    @Autowired
    private DomainServiceMessagePreprocessingComponent messagePreprocessingComponent;

    @Override
    public void prepareForRouting(DomainEntitySMSMessage message) {
        // Enhance the message with additional metadata required for routing
        message.getMetadata().put("routingKey", "exampleRoutingKey");

        // Update the message's status to reflect preprocessing
        message.setStatus(SharedEnumMessageStatusEnum.VALIDATED);

        // Format the message content as needed for downstream processing
        messagePreprocessingComponent.formatMessageContent(message);
    }
}