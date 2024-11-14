package ai.shreds.application.services;

import ai.shreds.application.exceptions.ApplicationException;
import ai.shreds.application.ports.ApplicationInputPortMessageReceptionPort;
import ai.shreds.application.ports.ApplicationOutputPortAcknowledgmentPort;
import ai.shreds.application.ports.ApplicationOutputPortMessagePreprocessingPort;
import ai.shreds.application.ports.ApplicationOutputPortMessageValidationPort;
import ai.shreds.domain.entities.DomainEntitySMSMessage;
import ai.shreds.domain.ports.DomainPortSMSMessageRepositoryPort;
import ai.shreds.shared.SharedEnumMessageStatusEnum;
import ai.shreds.shared.SharedResponseDTO;
import ai.shreds.shared.SharedSMSMessageDTO;
import ai.shreds.shared.SharedUtilDateUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationServiceMessageReceptionService implements ApplicationInputPortMessageReceptionPort {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationServiceMessageReceptionService.class);

    private final ApplicationOutputPortMessageValidationPort validationPort;
    private final ApplicationOutputPortMessagePreprocessingPort preprocessingPort;
    private final ApplicationOutputPortAcknowledgmentPort acknowledgmentPort;
    private final DomainPortSMSMessageRepositoryPort smsMessageRepositoryPort;

    @Override
    public SharedResponseDTO receiveMessage(SharedSMSMessageDTO messageDto) {
        try {
            DomainEntitySMSMessage domainMessage = new DomainEntitySMSMessage();
            domainMessage.setMessageId(messageDto.getMessageId());
            domainMessage.setRecipientNumber(messageDto.getRecipientNumber());
            domainMessage.setContent(messageDto.getContent());
            domainMessage.setMetadata(messageDto.getMetadata());
            domainMessage.setStatus(SharedEnumMessageStatusEnum.RECEIVED);
            domainMessage.setCreatedAt(SharedUtilDateUtil.getCurrentTimestamp());

            smsMessageRepositoryPort.save(domainMessage);

            List<DomainEntityValidationError> validationErrors = validationPort.validateMessage(domainMessage);
            if (!validationErrors.isEmpty()) {
                smsMessageRepositoryPort.updateStatus(domainMessage.getMessageId(), SharedEnumMessageStatusEnum.FAILED);
                return acknowledgmentPort.createErrorResponse(domainMessage.getMessageId(), validationErrors);
            }

            preprocessingPort.prepareForRouting(domainMessage);
            smsMessageRepositoryPort.updateStatus(domainMessage.getMessageId(), SharedEnumMessageStatusEnum.VALIDATED);
            return acknowledgmentPort.createSuccessResponse(domainMessage.getMessageId());
        } catch (ApplicationException e) {
            logger.error("ApplicationException occurred while processing message: {}", e.getMessage());
            return acknowledgmentPort.createErrorResponse(messageDto.getMessageId(), e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error during message reception", e);
            return acknowledgmentPort.createErrorResponse(messageDto.getMessageId(), "An unexpected error occurred.");
        }
    }
}