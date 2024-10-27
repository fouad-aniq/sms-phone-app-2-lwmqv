package ai.shreds.domain.repositories;

import ai.shreds.domain.entities.DomainEntityProcessedMessage;

public interface ProcessedMessageRepository {

    /**
     * Finds a processed message by its unique identifier.
     *
     * @param messageId the unique identifier of the processed message
     * @return the processed message entity if found, or null if not found
     */
    DomainEntityProcessedMessage findById(String messageId);

    /**
     * Saves a processed message entity to the repository.
     *
     * @param message the processed message entity to be saved
     */
    void save(DomainEntityProcessedMessage message);
}