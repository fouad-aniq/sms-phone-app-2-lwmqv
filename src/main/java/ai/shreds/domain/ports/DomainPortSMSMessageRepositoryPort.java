package ai.shreds.domain.ports;

import ai.shreds.domain.entities.DomainEntitySMSMessage;
import ai.shreds.shared.SharedEnumMessageStatusEnum;
import java.util.Optional;

public interface DomainPortSMSMessageRepositoryPort {

    /**
     * Persists the given SMSMessage entity to the database.
     * Must ensure that every SMSMessage has a unique messageId to prevent duplication and ensure traceability.
     * 
     * @param message the SMSMessage entity to save
     * @throws DataIntegrityViolationException if a duplicate messageId is detected
     */
    void save(DomainEntitySMSMessage message);

    /**
     * Retrieves an SMSMessage entity based on its unique messageId.
     * 
     * @param messageId the unique identifier of the SMSMessage
     * @return an Optional containing the SMSMessage if found, or empty if not found
     */
    Optional<DomainEntitySMSMessage> findByMessageId(String messageId);

    /**
     * Updates the status of an existing SMSMessage in the database.
     * Must ensure data integrity and consistency when performing the update, handling potential concurrency issues.
     * 
     * @param messageId the unique identifier of the SMSMessage
     * @param status the new status to set
     * @throws ResourceNotFoundException if the SMSMessage with the given messageId does not exist
     * @throws ConcurrencyException if a concurrency conflict is detected
     */
    void updateStatus(String messageId, SharedEnumMessageStatusEnum status);

}