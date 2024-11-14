package ai.shreds.application.services;

import ai.shreds.application.ports.ApplicationOutputPortMessageValidationPort;
import ai.shreds.domain.entities.DomainEntitySMSMessage;
import ai.shreds.domain.entities.DomainEntityValidationError;
import ai.shreds.domain.services.DomainServiceMessageValidationComponent;
import ai.shreds.domain.ports.DomainPortValidationErrorRepositoryPort;
import ai.shreds.domain.ports.DomainPortSMSMessageRepositoryPort;
import ai.shreds.shared.SharedValidationErrorDTO;
import ai.shreds.shared.SharedEnumMessageStatusEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ApplicationServiceMessageValidationService implements ApplicationOutputPortMessageValidationPort {

    private final DomainServiceMessageValidationComponent validationComponent;
    private final DomainPortValidationErrorRepositoryPort validationErrorRepository;
    private final DomainPortSMSMessageRepositoryPort smsMessageRepository;

    @Autowired
    public ApplicationServiceMessageValidationService(DomainServiceMessageValidationComponent validationComponent,
                                                      DomainPortValidationErrorRepositoryPort validationErrorRepository,
                                                      DomainPortSMSMessageRepositoryPort smsMessageRepository) {
        this.validationComponent = validationComponent;
        this.validationErrorRepository = validationErrorRepository;
        this.smsMessageRepository = smsMessageRepository;
    }

    @Override
    public List<SharedValidationErrorDTO> validateMessage(DomainEntitySMSMessage message) {
        // Check for unique messageId
        Optional<DomainEntitySMSMessage> existingMessage = smsMessageRepository.findByMessageId(message.getMessageId());
        if (existingMessage.isPresent()) {
            DomainEntityValidationError error = new DomainEntityValidationError();
            error.setMessageId(message.getMessageId());
            error.setErrorCode("DUPLICATE_MESSAGE_ID");
            error.setErrorMessage("Message ID must be unique.");
            error.setTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));
            validationErrorRepository.save(error);

            SharedValidationErrorDTO sharedError = new SharedValidationErrorDTO();
            sharedError.setErrorCode(error.getErrorCode());
            sharedError.setErrorMessage(error.getErrorMessage());
            sharedError.setTimestamp(error.getTimestamp());

            return List.of(sharedError);
        }

        // Validate recipient number
        List<DomainEntityValidationError> recipientErrors = validationComponent.validateRecipientNumber(message.getRecipientNumber());

        // Validate content
        List<DomainEntityValidationError> contentErrors = validationComponent.validateContent(message.getContent());

        // Validate metadata
        List<DomainEntityValidationError> metadataErrors = validationComponent.validateMetadata(message.getMetadata());

        // Combine all errors
        List<DomainEntityValidationError> allErrors = Stream.of(recipientErrors, contentErrors, metadataErrors)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        // Persist validation errors
        allErrors.forEach(validationErrorRepository::save);

        // Map domain validation errors to shared DTOs
        List<SharedValidationErrorDTO> validationErrorDTOs = allErrors.stream()
                .map(this::mapToSharedValidationErrorDTO)
                .collect(Collectors.toList());

        // Update message status
        if (validationErrorDTOs.isEmpty()) {
            smsMessageRepository.updateStatus(message.getMessageId(), SharedEnumMessageStatusEnum.VALIDATED);
        } else {
            smsMessageRepository.updateStatus(message.getMessageId(), SharedEnumMessageStatusEnum.FAILED);
        }

        return validationErrorDTOs;
    }

    private SharedValidationErrorDTO mapToSharedValidationErrorDTO(DomainEntityValidationError domainError) {
        SharedValidationErrorDTO dto = new SharedValidationErrorDTO();
        dto.setErrorCode(domainError.getErrorCode());
        dto.setErrorMessage(domainError.getErrorMessage());
        dto.setTimestamp(domainError.getTimestamp());
        return dto;
    }
}
