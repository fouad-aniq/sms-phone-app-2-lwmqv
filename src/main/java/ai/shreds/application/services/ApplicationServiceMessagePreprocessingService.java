package ai.shreds.application.services;

import ai.shreds.application.exceptions.ApplicationException;
import ai.shreds.application.ports.ApplicationOutputPortMessagePreprocessingPort;
import ai.shreds.domain.entities.DomainEntitySMSMessage;
import ai.shreds.domain.services.DomainServiceMessagePreprocessingComponent;
import ai.shreds.shared.SharedEnumMessageStatusEnum;
import ai.shreds.shared.SharedUtilDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;

@Service
public class ApplicationServiceMessagePreprocessingService implements ApplicationOutputPortMessagePreprocessingPort {

    private final DomainServiceMessagePreprocessingComponent domainServiceMessagePreprocessingComponent;

    @Autowired
    public ApplicationServiceMessagePreprocessingService(DomainServiceMessagePreprocessingComponent domainServiceMessagePreprocessingComponent) {
        this.domainServiceMessagePreprocessingComponent = domainServiceMessagePreprocessingComponent;
    }

    @Override
    public void prepareForRouting(DomainEntitySMSMessage message) {
        try {
            // Use the Decorator Pattern to add additional metadata
            DomainEntitySMSMessage decoratedMessage = decorateMessageWithMetadata(message);

            // Format message content
            domainServiceMessagePreprocessingComponent.formatMessageContent(decoratedMessage);

            // Prepare message for routing
            domainServiceMessagePreprocessingComponent.prepareMessageForRouting(decoratedMessage);

            // Update message status
            decoratedMessage.setStatus(SharedEnumMessageStatusEnum.VALIDATED);

            // Update updatedAt timestamp
            decoratedMessage.setUpdatedAt(Timestamp.valueOf(SharedUtilDateUtil.getCurrentTimestamp()));

        } catch (Exception e) {
            // Handle exceptions appropriately
            throw new ApplicationException("Error during message preprocessing", e);
        }
    }

    private DomainEntitySMSMessage decorateMessageWithMetadata(DomainEntitySMSMessage message) {
        // Implement the Decorator Pattern to add additional metadata
        if (message.getMetadata() == null) {
            message.setMetadata(new HashMap<>());
        }

        // Adding necessary metadata for routing
        message.getMetadata().put("routingKey", generateRoutingKey(message));
        message.getMetadata().put("processingNode", getProcessingNode());

        // Additional metadata can be added here

        return message;
    }

    private String generateRoutingKey(DomainEntitySMSMessage message) {
        // Logic to generate routing key based on message properties
        return "ROUTING_KEY_" + message.getRecipientNumber();
    }

    private String getProcessingNode() {
        // Logic to determine the processing node
        return "NodeA";
    }
}