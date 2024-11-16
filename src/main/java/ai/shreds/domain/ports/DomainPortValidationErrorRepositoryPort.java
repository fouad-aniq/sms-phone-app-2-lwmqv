package ai.shreds.domain.ports;

import ai.shreds.domain.entities.DomainEntityValidationError;
import java.util.List;

public interface DomainPortValidationErrorRepositoryPort {
    /**
     * Saves a validation error entity to the database.
     * 
     * @param validationError the validation error entity to be saved
     */
    void save(DomainEntityValidationError validationError);

    /**
     * Retrieves all validation errors associated with a specific messageId.
     * 
     * @param messageId the ID of the message whose validation errors are to be retrieved
     * @return a list of validation error entities associated with the given messageId
     */
    List<DomainEntityValidationError> findByMessageId(String messageId);
}
