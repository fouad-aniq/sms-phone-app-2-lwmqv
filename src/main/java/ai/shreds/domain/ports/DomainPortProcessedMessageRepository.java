package ai.shreds.domain.ports;

import ai.shreds.domain.entities.DomainEntityProcessedMessage;

public interface DomainPortProcessedMessageRepository {

    void save(DomainEntityProcessedMessage processedMessage);

    DomainEntityProcessedMessage findById(String id);

    DomainEntityProcessedMessage findByOriginalMessageId(String originalMessageId);

}