package ai.shreds.application.services;

import ai.shreds.application.ports.ApplicationOutputPortMessagePreprocessingPort;
import ai.shreds.domain.entities.DomainEntitySMSMessage;
import ai.shreds.domain.services.DomainServiceMessagePreprocessingComponent;
import ai.shreds.shared.SharedEnumMessageStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationServiceMessagePreprocessingService implements ApplicationOutputPortMessagePreprocessingPort {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationServiceMessagePreprocessingService.class);

    private final DomainServiceMessagePreprocessingComponent messagePreprocessingComponent;

    @Autowired
    public ApplicationServiceMessagePreprocessingService(DomainServiceMessagePreprocessingComponent messagePreprocessingComponent) {
        this.messagePreprocessingComponent = messagePreprocessingComponent;
    }

    @Override
    public void prepareForRouting(DomainEntitySMSMessage message) {
        try {
            // Format the message content for downstream processing
            messagePreprocessingComponent.formatMessageContent(message);

            // Enhance the message with additional metadata required for routing
            messagePreprocessingComponent.prepareMessageForRouting(message);

            // Update the message status to indicate it's ready for routing
            message.setStatus(SharedEnumMessageStatusEnum.VALIDATED);

        } catch (Exception e) {
            // Log the exception
            logger.error("Error during message preprocessing: {}", e.getMessage(), e);
            // Additional exception handling can be added here if necessary
        }
    }
}