package ai.shreds.domain.ports;

import ai.shreds.domain.entities.DomainEntityDeliveryDetails;

public interface DomainPortDeliveryDetailsRepository {

    /**
     * Saves or updates the DeliveryDetails associated with a message.
     *
     * @param details the DeliveryDetails entity to save
     */
    void save(DomainEntityDeliveryDetails details);

    /**
     * Retrieves the DeliveryDetails using the associated message ID.
     *
     * @param messageId the ID of the message
     * @return the DeliveryDetails associated with the given message ID
     */
    DomainEntityDeliveryDetails findByMessageId(String messageId);
}