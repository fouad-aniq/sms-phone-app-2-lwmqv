package ai.shreds.application.services;

import ai.shreds.application.exceptions.ApplicationException;
import ai.shreds.application.ports.ApplicationInputPortMessageReceptionPort;
import ai.shreds.application.ports.ApplicationOutputPortAcknowledgmentPort;
import ai.shreds.application.ports.ApplicationOutputPortMessagePreprocessingPort;
import ai.shreds.application.ports.ApplicationOutputPortMessageValidationPort;
import ai.shreds.domain.entities.DomainEntitySMSMessage;
import ai.shreds.domain.entities.DomainEntityValidationError;
import ai.shreds.domain.ports.DomainPortSMSMessageRepositoryPort;
import ai.shreds.domain.ports.DomainPortValidationErrorRepositoryPort;
import ai.shreds.shared.SharedEnumMessageStatusEnum;
import ai.shreds.shared.SharedResponseDTO;
import ai.shreds.shared.SharedSMSMessageDTO;
import ai.shreds.shared.SharedUtilDateUtil;
import ai.shreds.shared.SharedValidationErrorDTO;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationServiceMessageReceptionService implements ApplicationInputPortMessageReceptionPort {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationServiceMessageReceptionService.class);

    private final ApplicationOutputPortMessageValidationPort validationPort;
    private final ApplicationOutputPortMessagePreprocessingPort preprocessingPort;
    private final ApplicationOutputPortAcknowledgmentPort acknowledgmentPort;
    private final DomainPortSMSMessageRepositoryPort smsMessageRepositoryPort;
    private final DomainPortValidationErrorRepositoryPort validationErrorRepositoryPort;

    @Override
    public SharedResponseDTO receiveMessage(SharedSMSMessageDTO messageDto) {
        try {
            // Convert SharedSMSMessageDTO to DomainEntitySMSMessage
            DomainEntitySMSMessage domainMessage = convertToDomainEntity(messageDto);
            domainMessage.setStatus(SharedEnumMessageStatusEnum.RECEIVED);
            domainMessage.setCreatedAt(SharedUtilDateUtil.getCurrentTimestamp());

            // Save the message with status RECEIVED
            smsMessageRepositoryPort.save(domainMessage);

            // Validate the message
            List<SharedValidationErrorDTO> validationErrors = validationPort.validateMessage(domainMessage);

            if (validationErrors != null && !validationErrors.isEmpty()) {
                // Update status to FAILED
                smsMessageRepositoryPort.updateStatus(domainMessage.getMessageId(), SharedEnumMessageStatusEnum.FAILED);

                // Log validation errors
                logValidationErrors(domainMessage.getMessageId(), validationErrors);

                // Create error response
                return acknowledgmentPort.createErrorResponse(domainMessage.getMessageId(), validationErrors);
            } else {
                // Preprocess the message
                preprocessingPort.prepareForRouting(domainMessage);

                // Update status to VALIDATED
                smsMessageRepositoryPort.updateStatus(domainMessage.getMessageId(), SharedEnumMessageStatusEnum.VALIDATED);

                // Save the message
                smsMessageRepositoryPort.save(domainMessage);

                // Create success response
                return acknowledgmentPort.createSuccessResponse(domainMessage.getMessageId());
            }
        } catch (ApplicationException e) {
            logger.error("ApplicationException occurred while processing message: {}", e.getMessage());
            // Return error response
            return acknowledgmentPort.createErrorResponse(messageDto.getMessageId(), List.of(
                    new SharedValidationErrorDTO("APPLICATION_ERROR", e.getMessage(), SharedUtilDateUtil.getCurrentTimestamp())
            ));
        } catch (Exception e) {
            logger.error("Unexpected error during message reception", e);
            // Return error response
            return acknowledgmentPort.createErrorResponse(messageDto.getMessageId(), List.of(
                    new SharedValidationErrorDTO("INTERNAL_SERVER_ERROR", "An unexpected error occurred.", SharedUtilDateUtil.getCurrentTimestamp())
            ));
        }
    }

    private DomainEntitySMSMessage convertToDomainEntity(SharedSMSMessageDTO messageDto) {
        return new DomainEntitySMSMessage(
                messageDto.getMessageId(),
                messageDto.getRecipientNumber(),
                messageDto.getContent(),
                messageDto.getMetadata(),
                SharedEnumMessageStatusEnum.RECEIVED,
                SharedUtilDateUtil.getCurrentTimestamp(),
                SharedUtilDateUtil.getCurrentTimestamp()
        );
    }

    private void logValidationErrors(String messageId, List<SharedValidationErrorDTO> validationErrors) {
        List<DomainEntityValidationError> domainErrors = validationErrors.stream()
                .map(error -> new DomainEntityValidationError(
                        null,
                        messageId,
                        error.getErrorCode(),
                        error.getErrorMessage(),
                        SharedUtilDateUtil.getCurrentTimestamp()
                ))
                .collect(Collectors.toList());

        domainErrors.forEach(validationErrorRepositoryPort::save);
    }
}
