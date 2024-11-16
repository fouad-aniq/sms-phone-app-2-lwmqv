package ai.shreds.application.services;

import ai.shreds.application.exceptions.ApplicationException;
import ai.shreds.application.ports.ApplicationOutputPortMessageValidationPort;
import ai.shreds.domain.entities.DomainEntitySMSMessage;
import ai.shreds.domain.entities.DomainEntityValidationError;
import ai.shreds.domain.services.DomainServiceMessageValidationComponent;
import ai.shreds.shared.SharedValidationErrorDTO;
import ai.shreds.shared.SharedEnumMessageStatusEnum;
import ai.shreds.domain.ports.DomainPortValidationErrorRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationServiceMessageValidationService implements ApplicationOutputPortMessageValidationPort {

    private final DomainServiceMessageValidationComponent validationComponent;
    private final DomainPortValidationErrorRepositoryPort validationErrorRepository;

    @Autowired
    public ApplicationServiceMessageValidationService(DomainServiceMessageValidationComponent validationComponent,
                                                      DomainPortValidationErrorRepositoryPort validationErrorRepository) {
        this.validationComponent = validationComponent;
        this.validationErrorRepository = validationErrorRepository;
    }

    @Override
    public List<SharedValidationErrorDTO> validateMessage(DomainEntitySMSMessage message) throws ApplicationException {
        List<SharedValidationErrorDTO> validationErrors = new ArrayList<>();

        try {
            // Validate the message using the domain service component
            List<DomainEntityValidationError> domainErrors = validationComponent.validateMessage(message);

            // Convert domain validation errors to shared validation error DTOs
            for (DomainEntityValidationError domainError : domainErrors) {
                SharedValidationErrorDTO sharedError = new SharedValidationErrorDTO();
                sharedError.setErrorCode(domainError.getErrorCode());
                sharedError.setErrorMessage(domainError.getErrorMessage());
                sharedError.setTimestamp(domainError.getTimestamp());
                validationErrors.add(sharedError);
            }

            // Update message status based on validation result
            if (validationErrors.isEmpty()) {
                message.setStatus(SharedEnumMessageStatusEnum.VALIDATED);
            } else {
                message.setStatus(SharedEnumMessageStatusEnum.FAILED);
                validationErrors.forEach(error -> validationErrorRepository.save(new DomainEntityValidationError(
                        null, message.getMessageId(), error.getErrorCode(), error.getErrorMessage(), error.getTimestamp())));
            }

        } catch (Exception e) {
            throw new ApplicationException("Error validating SMS message", e);
        }

        return validationErrors;
    }
}
