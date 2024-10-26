package ai.shreds.domain.ports;

import ai.shreds.domain.entities.DomainEntityMessageProcessingEvent;

public interface DomainPortMessageProducer {
    void sendMessage(DomainEntityMessageProcessingEvent event);
}