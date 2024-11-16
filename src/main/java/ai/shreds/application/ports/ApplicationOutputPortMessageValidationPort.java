package ai.shreds.application.ports;

import ai.shreds.domain.entities.DomainEntitySMSMessage;
import ai.shreds.shared.SharedValidationErrorDTO;
import java.util.List;

public interface ApplicationOutputPortMessageValidationPort {
    /**
     * Validates the given SMS message according to business rules.
     *
     * @param message the SMS message to validate
     * @return a list of validation errors if any issues are found
     */
    List<SharedValidationErrorDTO> validateMessage(DomainEntitySMSMessage message);
}