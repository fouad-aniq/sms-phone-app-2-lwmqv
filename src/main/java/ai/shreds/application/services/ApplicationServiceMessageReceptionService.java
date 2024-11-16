package ai.shreds.application.services;

import ai.shreds.application.exceptions.ApplicationException;
import ai.shreds.application.ports.ApplicationInputPortMessageReceptionPort;
import ai.shreds.application.ports.ApplicationOutputPortMessageValidationPort;
import ai.shreds.application.ports.ApplicationOutputPortMessagePreprocessingPort;
import ai.shreds.application.ports.ApplicationOutputPortAcknowledgmentPort;
import ai.shreds.domain.ports.DomainPortSMSMessageRepositoryPort;
import ai.shreds.shared.SharedSMSMessageDTO;
import ai.shreds.shared.SharedResponseDTO;
import ai.shreds.shared.SharedValidationErrorDTO;
import ai.shreds.domain.entities.DomainEntitySMSMessage;
import ai.shreds.shared.SharedEnumMessageStatusEnum;
import ai.shreds.shared.SharedUtilDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationServiceMessageReceptionService implements ApplicationInputPortMessageReceptionPort {

    private final ApplicationOutputPortMessageValidationPort validationPort;
    private final ApplicationOutputPortMessagePreprocessingPort preprocessingPort;
    private final ApplicationOutputPortAcknowledgmentPort acknowledgmentPort;
    private final DomainPortSMSMessageRepositoryPort messageRepositoryPort;

    @Override
    public SharedResponseDTO receiveMessage(SharedSMSMessageDTO messageDto) {
        try {
            // Convert DTO to Domain Entity
            DomainEntitySMSMessage domainMessage = new DomainEntitySMSMessage();
            domainMessage.setMessageId(messageDto.getMessageId());
            domainMessage.setRecipientNumber(messageDto.getRecipientNumber());
            domainMessage.setContent(messageDto.getContent());
            domainMessage.setMetadata(messageDto.getMetadata());
            domainMessage.setStatus(SharedEnumMessageStatusEnum.RECEIVED);
            domainMessage.setCreatedAt(SharedUtilDateUtil.getCurrentTimestamp());
            domainMessage.setUpdatedAt(SharedUtilDateUtil.getCurrentTimestamp());

            // Check for unique messageId
            Optional<DomainEntitySMSMessage> existingMessage = messageRepositoryPort.findByMessageId(domainMessage.getMessageId());
            if (existingMessage.isPresent()) {
                return acknowledgmentPort.createErrorResponse(domainMessage.getMessageId(), List.of(new SharedValidationErrorDTO("DUPLICATE_ID", "Message ID already exists", SharedUtilDateUtil.getCurrentTimestamp())));
            }

            // Validate Message
            List<SharedValidationErrorDTO> validationErrors = validationPort.validateMessage(domainMessage);
            if (!validationErrors.isEmpty()) {
                return acknowledgmentPort.createErrorResponse(domainMessage.getMessageId(), validationErrors);
            }

            // Preprocess Message
            preprocessingPort.prepareForRouting(domainMessage);

            // Create Success Response
            return acknowledgmentPort.createSuccessResponse(domainMessage.getMessageId());

        } catch (Exception e) {
            // Throw ApplicationException
            throw new ApplicationException("Error processing message: " + e.getMessage(), e);
        }
    }
}
