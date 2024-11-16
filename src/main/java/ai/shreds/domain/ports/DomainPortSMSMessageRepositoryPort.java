package ai.shreds.domain.ports;

import ai.shreds.domain.entities.DomainEntitySMSMessage;
import ai.shreds.shared.SharedEnumMessageStatusEnum;

import java.util.Optional;

/**
 * Interface for managing database operations for SMSMessage entities.
 * Provides methods to save, retrieve, and update SMSMessage entities.
 */
public interface DomainPortSMSMessageRepositoryPort {

    /**
     * Persists an SMSMessage entity to the database.
     * Ensures that every SMS message has a unique messageId to prevent duplication and ensure traceability.
     * Implement proper synchronization mechanisms and atomic operations to ensure data integrity under high load.
     *
     * @param message the SMSMessage entity to be saved
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
     * The status should reflect whether the message has been 'validated' or has 'failed'.
     * Use atomic operations to prevent inconsistent data states due to race conditions or concurrent modifications.
     *
     * @param messageId the unique identifier of the SMSMessage
     * @param status    the new status of the SMSMessage
     */
    void updateStatus(String messageId, SharedEnumMessageStatusEnum status);
}