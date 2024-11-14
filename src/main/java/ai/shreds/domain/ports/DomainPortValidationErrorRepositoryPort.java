package ai.shreds.domain.ports;

import ai.shreds.domain.entities.DomainEntityValidationError;
import java.util.List;

/**
 * Handles database interactions for ValidationError entities associated with SMS messages.
 */
public interface DomainPortValidationErrorRepositoryPort {
    /**
     * Stores a ValidationError entity in the database.
     * Ensures that the error codes and messages are standardized and do not expose sensitive information.
     *
     * @param validationError the validation error entity to be saved
     */
    void save(DomainEntityValidationError validationError);

    /**
     * Retrieves all validation errors associated with a specific messageId.
     *
     * @param messageId the message ID to retrieve validation errors for
     * @return a list of validation error entities associated with the given messageId
     */
    List<DomainEntityValidationError> findByMessageId(String messageId);
}