package ai.shreds.domain.ports;

import ai.shreds.domain.entities.DomainEntityProcessedMessage;

/**
 * Interface defining the contract for sending messages to the SMS Gateway Integration Service.
 * This abstraction allows the domain layer to remain decoupled from the infrastructure layer.
 */
public interface DomainPortSmsGatewayClient {

    /**
     * Sends a processed message to the SMS Gateway Integration Service.
     *
     * @param message the processed message to send.
     */
    void sendMessage(DomainEntityProcessedMessage message);
}