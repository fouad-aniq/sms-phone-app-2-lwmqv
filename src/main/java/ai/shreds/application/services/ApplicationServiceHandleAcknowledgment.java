package ai.shreds.application.services;

import ai.shreds.application.ports.ApplicationInputPortHandleAcknowledgment;
import ai.shreds.application.mappers.ApplicationProcessedMessageMapper;
import ai.shreds.domain.services.DomainServiceAcknowledgment;
import ai.shreds.shared.SharedDeliveryAcknowledgmentDTO;
import ai.shreds.shared.SharedErrorNotificationDTO;
import ai.shreds.domain.value_objects.DomainValueDeliveryAcknowledgment;
import ai.shreds.domain.value_objects.DomainValueErrorNotification;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class ApplicationServiceHandleAcknowledgment implements ApplicationInputPortHandleAcknowledgment {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationServiceHandleAcknowledgment.class);
    private final ApplicationProcessedMessageMapper mapper;
    private final DomainServiceAcknowledgment domainServiceAcknowledgment;

    @Override
    public void processAcknowledgment(SharedDeliveryAcknowledgmentDTO acknowledgmentDTO) {
        try {
            DomainValueDeliveryAcknowledgment domainAcknowledgment = mapper.toDomainDeliveryAcknowledgment(acknowledgmentDTO);
            domainServiceAcknowledgment.processAcknowledgment(domainAcknowledgment);
        } catch (Exception e) {
            logger.error("Error processing delivery acknowledgment: " + e.getMessage(), e);
            // Consider rethrowing or throwing a custom exception based on business logic
        }
    }

    @Override
    public void processErrorNotification(SharedErrorNotificationDTO errorNotificationDTO) {
        try {
            DomainValueErrorNotification domainErrorNotification = mapper.toDomainErrorNotification(errorNotificationDTO);
            domainServiceAcknowledgment.processErrorNotification(domainErrorNotification);
        } catch (Exception e) {
            logger.error("Error processing error notification: " + e.getMessage(), e);
            // Consider rethrowing or throwing a custom exception based on business logic
        }
    }

}